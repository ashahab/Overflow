CREATE TABLE "Question"(
      "id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      "Title" TEXT,
      "Body" TEXT,
      "Author" VARCHAR(255),
      "Posted" TIMESTAMP
 );
  CREATE TABLE "Comment"(
      "id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      "Author" VARCHAR(255),
      "Text" TEXT,
      "Posted" TIMESTAMP
 );
 CREATE TABLE "Comment_join_Question_Comment"(
    "id" BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    "Question_id" BIGINT,
    "Comment_id" BIGINT
 );
 CREATE TABLE "Comment_join_Answer_Comment"(
     "id" BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
     "Answer_id" BIGINT,
     "Comment_id" BIGINT
  );
 CREATE TABLE "Answer"(
       "id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       "Question_id" BIGINT,
       "Text" TEXT,
       "Author" VARCHAR(255),
       "Posted" TIMESTAMP,
       "Answered" BOOLEAN
  );
