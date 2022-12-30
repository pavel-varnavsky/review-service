create table if not exists review (
    id bigserial,
    product_id varchar(20),
    score int,
  primary key (id)
)