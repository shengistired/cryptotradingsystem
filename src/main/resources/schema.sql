CREATE TABLE crypto_wallet(
    user_id VARCHAR(100) NOT NULL PRIMARY KEY,
    usdt DECIMAL(30, 10) NOT NULL,
    latest_transaction VARCHAR(36) NULL,
    latest_purchase_timestamp TIMESTAMP NULL
);

insert into crypto_wallet(user_id,usdt,latest_transaction,latest_purchase_timestamp) values ('testUser1',50000,NULL,NULL);
insert into crypto_wallet(user_id,usdt,latest_transaction,latest_purchase_timestamp) values ('testUser2',50000,NULL,NULL);
insert into crypto_wallet(user_id,usdt,latest_transaction,latest_purchase_timestamp) values ('testUser3',50000,NULL,NULL);
insert into crypto_wallet(user_id,usdt,latest_transaction,latest_purchase_timestamp) values ('testUser4',50000,NULL,NULL);

CREATE TABLE crypto_transaction_history(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    symbol VARCHAR(10) NOT NULL,
    bid_price DECIMAL(13, 5) NOT NULL,
    bid_qty DECIMAL(13, 5) NOT NULL,
    ask_price DECIMAL(13, 5) NOT NULL,
    ask_qty VARCHAR(36) NULL,
    source_from VARCHAR(30) NOT NULL,
    update_timestamp TIMESTAMP NULL
);

CREATE TABLE crypto_item(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    qty DECIMAL(13, 5) NOT NULL,
    crypto_value DECIMAL(30, 10) NOT NULL,
    latest_transaction VARCHAR(36) NULL,
    latest_purchase_timestamp TIMESTAMP NULL
);

insert into crypto_item(id,symbol,user_id,qty,crypto_value,latest_transaction,latest_purchase_timestamp) values ('randomId1','ETHUSDT','testUser1',0,0,NULL,NULL);
insert into crypto_item(id,symbol,user_id,qty,crypto_value,latest_transaction,latest_purchase_timestamp) values ('randomId2','ETHUSDT','testUser2',0,0,NULL,NULL);
insert into crypto_item(id,symbol,user_id,qty,crypto_value,latest_transaction,latest_purchase_timestamp) values ('randomId3','ETHUSDT','testUser3',0,0,NULL,NULL);
insert into crypto_item(id,symbol,user_id,qty,crypto_value,latest_transaction,latest_purchase_timestamp) values ('randomId4','ETHUSDT','testUser4',0,0,NULL,NULL);
insert into crypto_item(id,symbol,user_id,qty,crypto_value,latest_transaction,latest_purchase_timestamp) values ('randomId5','BTCUSDT','testUser1',0,0,NULL,NULL);
insert into crypto_item(id,symbol,user_id,qty,crypto_value,latest_transaction,latest_purchase_timestamp) values ('randomId6','BTCUSDT','testUser2',0,0,NULL,NULL);
insert into crypto_item(id,symbol,user_id,qty,crypto_value,latest_transaction,latest_purchase_timestamp) values ('randomId7','BTCUSDT','testUser3',0,0,NULL,NULL);
insert into crypto_item(id,symbol,user_id,qty,crypto_value,latest_transaction,latest_purchase_timestamp) values ('randomId8','BTCUSDT','testUser4',0,0,NULL,NULL);

CREATE TABLE crypto_data(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL,
    bid_price DECIMAL(30, 10) NULL,
    bid_qty DECIMAL(30, 10) NULL,
    ask_price DECIMAL(30, 10) NULL,
    ask_qty DECIMAL(30, 10) NULL,
    source VARCHAR(36) NULL,
    update_timestamp TIMESTAMP NULL
);