CREATE TABLE copyright
(
    id           BIGSERIAL PRIMARY KEY,
    recording_id BIGINT REFERENCES recording (id),
    company_id   BIGINT REFERENCES company (id),
    start_date   TIMESTAMP,
    end_date     TIMESTAMP,
    tax          NUMERIC
);

INSERT INTO copyright (recording_id, company_id, start_date, end_date, tax ) VALUES
    (1, 1, '2008-01-01', '2038-12-31', 10.95);

INSERT INTO copyright (recording_id, company_id, start_date, end_date, tax ) VALUES
    (1, 2, '2010-02-23', '2028-12-31', 6.66);

INSERT INTO copyright (recording_id, company_id, start_date, end_date, tax ) VALUES
    (2, 3, '2012-03-08', '2022-12-31', 9.99);

INSERT INTO copyright (recording_id, company_id, start_date, end_date, tax ) VALUES
    (4, 1, '2020-05-01', '2023-12-31', 3.33);

INSERT INTO copyright (recording_id, company_id, start_date, end_date, tax ) VALUES
    (4, 3, '2021-05-09', '2024-12-31', 4.44);

INSERT INTO copyright (recording_id, company_id, start_date, end_date, tax ) VALUES
    (4, 4, '2019-06-12', '2025-12-31', 5.55);
