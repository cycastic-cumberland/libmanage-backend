create table authors
(
    id   bigint unsigned auto_increment
        primary key,
    name varchar(128) charset utf8mb3 not null,
    constraint id
        unique (id)
);

create index authors_name_index
    on authors (name);

create table books
(
    id             bigint unsigned auto_increment
        primary key,
    title          varchar(128) charset utf8mb3 not null,
    isbn           varchar(16)                  not null,
    published_date datetime                     not null,
    in_storage     int                          not null,
    capsule_url    varchar(128)                 not null,
    constraint id
        unique (id)
);

create table book_authors
(
    book_id   bigint unsigned not null,
    author_id bigint unsigned not null,
    primary key (book_id, author_id),
    constraint book_authors_authors_id_fk
        foreign key (author_id) references authors (id),
    constraint book_authors_books_id_fk
        foreign key (book_id) references books (id)
);

create index books_title_index
    on books (title);

create table employees
(
    id              varchar(32)                 not null
        primary key,
    full_name       varchar(64) charset utf8mb3 not null,
    hashed_password varchar(100)                not null,
    role            varchar(20)                 not null
);

create index employees_full_name_index
    on employees (full_name);

create table members
(
    id        varchar(32)                 not null
        primary key,
    full_name varchar(64) charset utf8mb3 not null,
    join_date timestamp                   not null
);

create table borrowed_books
(
    ticket_id   bigint unsigned auto_increment
        primary key,
    member_id   varchar(32)     not null,
    book_id     bigint unsigned not null,
    borrowed_at datetime        not null,
    approved_by varchar(32)     not null,
    returned    tinyint(1)      not null,
    constraint ticket_id
        unique (ticket_id),
    constraint borrowed_books_books_id_fk
        foreign key (book_id) references books (id),
    constraint borrowed_books_employees_id_fk
        foreign key (approved_by) references employees (id),
    constraint borrowed_books_members_id_fk
        foreign key (member_id) references members (id)
);

create index members_full_name_index
    on members (full_name);

create table publisher
(
    id             bigint unsigned auto_increment
        primary key,
    publisher_name varchar(64) charset utf8mb3 not null,
    email          varchar(64)                 not null,
    constraint id
        unique (id)
);

create table purchase_batches
(
    id             bigint unsigned auto_increment
        primary key,
    purchase_date  timestamp       not null,
    from_publisher bigint unsigned not null,
    cost           bigint          not null,
    approved_by    varchar(32)     not null,
    constraint id
        unique (id),
    constraint purchase_batches_employees_id_fk
        foreign key (approved_by) references employees (id),
    constraint purchase_batches_publisher_id_fk
        foreign key (from_publisher) references publisher (id)
);

create table batch_details
(
    batch_id bigint unsigned not null,
    book_id  bigint unsigned not null,
    amount   int             not null,
    primary key (book_id, batch_id),
    constraint batch_details_books_id_fk
        foreign key (book_id) references books (id),
    constraint batch_details_purchase_batches_id_fk
        foreign key (batch_id) references purchase_batches (id)
);

create table shelves
(
    id         varchar(8)                  not null
        primary key,
    shelf_name varchar(32) charset utf8mb3 not null
);

create table book_locations
(
    id       bigint unsigned auto_increment
        primary key,
    book_id  bigint unsigned not null,
    shelf_id varchar(8)      not null,
    constraint id
        unique (id),
    constraint book_locations_books_id_fk
        foreign key (book_id) references books (id),
    constraint book_locations_shelves_id_fk
        foreign key (shelf_id) references shelves (id)
);

create index shelves_shelf_name_index
    on shelves (shelf_name);

