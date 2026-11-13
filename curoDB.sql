-- Database Creation
CREATE DATABASE curo_db;

USE  curo_db

-- staff Table
CREATE TABLE staff(
	staff_id VARCHAR(50) PRIMARY KEY,
	staff_name VARCHAR(50) NOT NULL,
	role_ VARCHAR(50) NOT NULL CHECK (role_ IN ('OWNER', 'CASHIER')),
	contact_number VARCHAR(10) NOT NULL,
	password_ VARCHAR(50) NOT NULL,
	status_ VARCHAR(50) DEFAULT 'ACTIVE' CHECK (status_ IN ('ACTIVE', 'INACTIVE'))
);

-- inventory Table
CREATE TABLE inventory(
	medicine_id INT IDENTITY(1,1) PRIMARY KEY,
	medicine_name VARCHAR(50) NOT NULL,
	company_name VARCHAR(50) NOT NULL,
	batch_number VARCHAR(50) NOT NULL,
	unit_price DECIMAL(10,2) NOT NULL,
	stock_quantity INT NOT NULL DEFAULT 0,
	stock_date DATE NOT NULL,
	exp_date DATE NOT NULL,
	requires_prescription BIT DEFAULT 0,
	moving_type VARCHAR(10) DEFAULT 'SLOW' CHECK (moving_type IN ('FAST', 'SLOW'))
);

-- bills Table
CREATE TABLE bills(
	bill_id INT IDENTITY(1,1) PRIMARY KEY,
	cashier_id VARCHAR(50) FOREIGN KEY REFERENCES staff(staff_id),
	total_amount DECIMAL(10,2) NOT NULL,
	discount_amount DECIMAL(10,2) NOT NULL,
	net_total DECIMAL(10,2) NOT NULL,
	is_prescription_checked BIT DEFAULT 0,
	is_free_issue BIT DEFAULT 0,
	create_at DATETIME DEFAULT GETDATE()
);

--bills item Table
CREATE TABLE bill_item(
	item_id INT IDENTITY(1,1) PRIMARY KEY,
	bill_id INT FOREIGN KEY REFERENCES bills(bill_id),
	medicine_id INT FOREIGN KEY REFERENCES inventory(medicine_id),
	quantity INT NOT NULL,
	line_total DECIMAL(10,2) NOT NULL 
);

--canceled bills Table
CREATE TABLE canceled_bills(
	cancel_id INT IDENTITY(1,10) PRIMARY KEY,
	bill_id INT NOT NULL,
	canceled_by VARCHAR(50) FOREIGN KEY REFERENCES staff(staff_id),
	canceled_at DATETIME DEFAULT GETDATE(),
	reason VARCHAR(50)
);

--Default Admin User
INSERT INTO staff (staff_id,staff_name,role_,password_,status_)
VALUES ('ADMIN01','System Owner','OWNER','ict25954','ACTIVE');
