

CREATE TABLE
cardinfo(entry_num int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
card_name varchar(40) NOT NULL UNIQUE,
set varchar(5) NOT NULL,
num int NOT NULL,
bought_price numeric NOT NULL,
price numeric);

cd C:\Program Files\PostgreSQL\17\bin>
psql -U postgres