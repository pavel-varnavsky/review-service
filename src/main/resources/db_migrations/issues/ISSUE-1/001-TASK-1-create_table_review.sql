create table if not exists review
(
    id         bigserial,
    product_id varchar(20),
    score      int,
    primary key (id)
);

create index if not exists idx_product_id on review using btree (product_id);