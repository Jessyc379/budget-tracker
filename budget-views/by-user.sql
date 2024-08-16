SELECT * FROM budget.users;
CREATE VIEW transactions_by_user AS
SELECT user_name
	, vendor_name
    , transaction_date
    , amount
    , notes
    , category_id
FROM transactions
INNER JOIN users on transactions.user_id = users.user_id
INNER JOIN vendors on transactions.vendor_id = vendors.vendor_id
-- INNER JOIN categories on transactions.category_id = category.category_id;
