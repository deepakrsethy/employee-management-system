CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO drs_ems.department (id, department_name)
VALUES
  (1, 'Engineering'),
  (2, 'Human Resources'),
  (3, 'Sales'),
  (4, 'Marketing'),
  (5, 'Product Management');


 DO $$
BEGIN
  FOR i IN 1..10000 LOOP
    INSERT INTO drs_ems.address (id, address_line1, address_line2, city, state, country, pin_code)
    VALUES (
      uuid_generate_v4(),
      'House No ' || (100 + i),
      'Tech Park Phase ' || (i % 5 + 1),
      CASE (i % 5)
        WHEN 0 THEN 'Bangalore'
        WHEN 1 THEN 'Hyderabad'
        WHEN 2 THEN 'Pune'
        WHEN 3 THEN 'Chennai'
        ELSE 'Gurgaon'
      END,
      CASE (i % 5)
        WHEN 0 THEN 'Karnataka'
        WHEN 1 THEN 'Telangana'
        WHEN 2 THEN 'Maharashtra'
        WHEN 3 THEN 'Tamil Nadu'
        ELSE 'Haryana'
      END,
      'India',
      LPAD(CAST(560000 + (i % 5000) AS TEXT), 6, '0')
    );
  END LOOP;
END$$;


DO $$
DECLARE
  dept_count INT := 5;
  addr RECORD;
  i INT := 0;
BEGIN
  FOR addr IN SELECT id FROM drs_ems.address LOOP
    i := i + 1;
    INSERT INTO drs_ems.employee (
      name, designation, gender, email, department_id,
      salary, joining_date, phone_number, address_id
    )
    VALUES (
      'Employee_' || i,
      CASE (i % 5)
        WHEN 0 THEN 'Software Engineer'
        WHEN 1 THEN 'Senior Developer'
        WHEN 2 THEN 'Team Lead'
        WHEN 3 THEN 'QA Engineer'
        ELSE 'Product Manager'
      END,
      CASE (i % 2)
        WHEN 0 THEN 'Male'
        ELSE 'Female'
      END,
      'employee' || i || '@company.com',
      (i % dept_count) + 1,
      ROUND(((400000+RANDOM()) * 100000)::numeric, 2),
      CURRENT_DATE - (i % 1000),
      '9' || LPAD(CAST(100000000 + i AS TEXT), 9, '0'),
      addr.id
    );
    EXIT WHEN i >= 10000;
  END LOOP;
END$$;