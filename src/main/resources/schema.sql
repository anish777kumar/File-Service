CREATE TABLE my_documents(
my_document_id INT AUTO_INCREMENT  PRIMARY KEY,
my_document_name VARCHAR(250) NOT NULL,
my_users_name varchar(100),
my_document_type varchar(5)
);

CREATE SEQUENCE my_documents_sequence_id START WITH (select max(my_document_id) + 1 from my_documents);
