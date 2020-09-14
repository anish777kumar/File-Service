package com.bunnu.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bunnu.entity.Documents;
import com.bunnu.exception.DocumentStorageException;
import com.bunnu.exception.UnAuthorisedOperationException;
import com.bunnu.repositary.DocumentsRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class DocumentsService.
 */
@Service
public class DocumentsService {

	/** The doc repository. */
	@Autowired
	DocumentsRepository docRepository;

	/** The env. */
	@Autowired
	Environment env;

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(DocumentsService.class);

	/**
	 * Gets the file by.
	 *
	 * @param nameOrId the name or id
	 * @param userName the user name
	 * @return the file by
	 * @throws FileNotFoundException the file not found exception
	 */
	public Optional<File> getFileBy(String nameOrId, String userName,boolean toDelete) throws FileNotFoundException {
		int id = -1;
		String basepath = System.getProperty("user.dir") + File.separator + env.getProperty("documents.dir.home");
		File actualFile = null;
		Documents doc;
		Optional<Documents> opt;
		log.info("basePath is :" + basepath);

		// check if the given filter is name or id
		try {
			id = Integer.parseInt(nameOrId);
			log.info("Provided filter is ID of file");
		} catch (NumberFormatException nfe) {
			log.info("Provided filter is name of file ");
		}

		// identify the doc in db by id or name
		log.info("the docs in table is :"+docRepository.count());
		if (id != -1) {
			
			opt = docRepository.findAll().stream().filter(d -> d.getMy_document_id()==Integer.parseInt(nameOrId)).findFirst();
		} else {
			opt= docRepository.findAll().stream().filter(d -> d.getMy_document_name().equals(nameOrId))
					.findFirst();
		}
		
		log.info("The opt is :"+opt);
		if(opt.isPresent())
		{
			doc = opt.get();
			if (authorised(doc, userName)) {
				actualFile = new File(basepath + File.separator + doc.getMy_document_name());
				
				if(toDelete && doc.getMy_users_name()!=null && !doc.getMy_users_name().isEmpty())
					docRepository.delete(doc);
			}
			else
			 throw new UnAuthorisedOperationException("user :"+userName+"  is UNAUTHORIZED to perform operations on this file :"+nameOrId);
		}
		else
		{
			throw new FileNotFoundException("No file found with filter :"+nameOrId);
		}
		// if user is authorised , get the file
		 
		log.info("Actual File is :"+actualFile);
		// check if file exists
		actualFile = actualFile != null && actualFile.exists() ? actualFile : null;
		log.info("The actualFile for nameOrId :" + nameOrId + " is :" + actualFile);
		// return file
		return Optional.ofNullable(actualFile);
	}

	/**
	 * Authorised.
	 *
	 * @param doc the doc
	 * @param userName the user name
	 * @return true, if successful
	 */
	// check if user is authorised
	private boolean authorised(Documents doc, String userName) {
		boolean authority = false;
		if (doc != null) {
			if (doc.getMy_users_name() != null && !doc.getMy_users_name().isEmpty()) {
				authority = doc.getMy_users_name().equalsIgnoreCase(userName);
			} else
				authority = true; // no username , means any one can download the file
		}

		return authority;

	}

	/**
	 * Load file as resource.
	 *
	 * @param file the file
	 * @return the resource
	 * @throws Exception the exception
	 */
	public Resource loadFileAsResource(File file) throws Exception {

		String basepath = System.getProperty("user.dir") + File.separator + env.getProperty("documents.dir.home");
		log.info("loadFileAsResource ,basePath is :" + basepath);
		Path filePath = Paths.get(file.getAbsolutePath());

		Resource resource = (Resource) new UrlResource(filePath.toUri());
		return resource;

	}

	/**
	 * Store file.
	 *
	 * @param file the file
	 * @param userName the user name
	 * @return the documents
	 */
	public Documents storeFile(MultipartFile file, String userName) {

		if (file == null || file.isEmpty())
			throw new DocumentStorageException("Invalid file to store .");

		// Normalize file name
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		log.info("Orignal filename :" + originalFileName);

		try {

			// Check if the file's name contains invalid characters
			if (originalFileName.contains("..")) {
				throw new DocumentStorageException(
						"Sorry! Filename contains invalid path sequence " + originalFileName);
			}
			int t = originalFileName.lastIndexOf('.');
			String fileType = t != -1 ? originalFileName.substring(t) : null;

			// file path to copy
			String basepath = System.getProperty("user.dir") + File.separator + env.getProperty("documents.dir.home");
			File to = new File(basepath + File.separator + originalFileName);

			if (to.exists()) {
				originalFileName = "Copy of -" + originalFileName;
				to = new File(basepath + File.separator + originalFileName);
			}

			to.createNewFile();

			// copying of files
			FileChannel source = ((FileInputStream) file.getInputStream()).getChannel();
			FileChannel target = new FileOutputStream(to).getChannel();

			source.transferTo(0, source.size(), target);
			source.close();
			target.close();

			// saving the record detail in db
			Documents doc = new Documents();
			doc.setMy_document_name(originalFileName);
			doc.setMy_users_name(userName);
			doc.setMy_document_type(fileType);

			docRepository.saveAndFlush(doc);

			return doc;

		} catch (IOException ex) {
			ex.printStackTrace();
			throw new DocumentStorageException(
					"Could not store file " + originalFileName + ". Please try again!" + ex.getMessage());

		}

	}

}
