create table if not exists books(
    id uuid,
    title text,
    publication_date date,
    primary key(id)
);

create table if not exists authors(
    id uuid,
    first_name varchar(128),
    last_name varchar(128),
    primary key(id)
);

create table if not exists authorships(
    book_id uuid,
    author_id uuid,
    primary key(book_id, author_id)
);

