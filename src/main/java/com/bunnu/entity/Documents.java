package com.bunnu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Documents.
 */
@Entity
@Table(name = "my_documents")
public class Documents {

	/** The document id. */
	@Id
	@SequenceGenerator(name= "my_documents_sequence", sequenceName = "my_documents_sequence_id", initialValue=1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_documents_sequence")
	@Column(name = "my_document_id")
	private Integer my_document_id;

	/** The document name. */
	@Column(name = "my_document_name")
	private String my_document_name;

	/** The User name. */
	@Column(name = "my_users_name")
	private String my_users_name;
	
	/** The document type. */
	@Column(name = "my_document_type")
	private String my_document_type;

	/**
	 * Instantiates a new documents.
	 */
	public Documents()
	{
		
	}

	/**
	 * Gets the my document id.
	 *
	 * @return the my document id
	 */
	public Integer getMy_document_id() {
		return my_document_id;
	}

	/**
	 * Sets the my document id.
	 *
	 * @param my_document_id the new my document id
	 */
	public void setMy_document_id(Integer my_document_id) {
		this.my_document_id = my_document_id;
	}

	/**
	 * Gets the my document name.
	 *
	 * @return the my document name
	 */
	public String getMy_document_name() {
		return my_document_name;
	}

	/**
	 * Sets the my document name.
	 *
	 * @param my_document_name the new my document name
	 */
	public void setMy_document_name(String my_document_name) {
		this.my_document_name = my_document_name;
	}

	/**
	 * Gets the my users name.
	 *
	 * @return the my users name
	 */
	public String getMy_users_name() {
		return my_users_name;
	}

	/**
	 * Sets the my users name.
	 *
	 * @param my_users_name the new my users name
	 */
	public void setMy_users_name(String my_users_name) {
		this.my_users_name = my_users_name;
	}

	/**
	 * Gets the my document type.
	 *
	 * @return the my document type
	 */
	public String getMy_document_type() {
		return my_document_type;
	}

	/**
	 * Sets the my document type.
	 *
	 * @param my_document_type the new my document type
	 */
	public void setMy_document_type(String my_document_type) {
		this.my_document_type = my_document_type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Documents [document_id=" + my_document_id + ", document_name=" + my_document_name
				+ ", users_name=" + my_users_name + ", document_type=" + my_document_type + "]";
	}
	

}
