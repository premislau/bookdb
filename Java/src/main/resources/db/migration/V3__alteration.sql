ALTER TABLE authorships
ADD CONSTRAINT fk_book
FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;

ALTER TABLE authorships
ADD CONSTRAINT fk_author
FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE;