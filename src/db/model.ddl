CREATE TABLE "Post"(
      "id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      "Title" TEXT,
      "Body" TEXT,
      "Posted" TIMESTAMP
 );
  CREATE TABLE "Comment"(
      "id" BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      "Post_id" BIGINT,
      "Name" VARCHAR(255),
      "Text" TEXT,
      "Posted" TIMESTAMP
 );