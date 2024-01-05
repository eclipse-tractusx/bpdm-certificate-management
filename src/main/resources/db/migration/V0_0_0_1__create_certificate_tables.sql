CREATE SEQUENCE IF NOT EXISTS certificate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE certificate_types (
	id int8                                     NOT NULL,
	created_at timestamptz(6)                   NOT NULL,
	updated_at timestamptz(6)                   NOT NULL,
	uuid uuid                                   NOT NULL,
	certificate_type varchar(255)               NOT NULL,
	certificate_version varchar(255)            NOT NULL,
	CONSTRAINT certificate_types_pkey PRIMARY KEY (id),
	CONSTRAINT uk_ggv2gonw2iwmeam73w5nj47gf UNIQUE (uuid),
	CONSTRAINT ukatx1fa34u13spk88e0qdbujbw UNIQUE (certificate_type, certificate_version)
);

CREATE TABLE document (
	id int8                                     NOT NULL,
	certificate_file text                       NOT NULL,
	certificate_format varchar(255)             NOT NULL,
	CONSTRAINT document_certificate_format_check CHECK (((certificate_format)::text = ANY ((ARRAY['PDF'::character varying, 'PNG'::character varying, 'JPG'::character varying])::text[]))),
	CONSTRAINT document_pkey PRIMARY KEY (id)
);

CREATE TABLE enclosed_sites (
	certificate_id int8                         NOT NULL,
	area_of_application varchar(255)            NULL,
	site_bpn varchar(255)                       NULL
);

CREATE TABLE certificate (
	id int8                                 NOT NULL,
	created_at TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
	updated_at TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
	uuid uuid                               NOT NULL,
	area_of_application varchar(255)        NULL,
	business_partner_number varchar(255)    NULL,
	document_id uuid                        NULL,
	issuer varchar(255)                     NULL,
	registration_number varchar(255)        NULL,
	remark varchar(255)                     NULL,
	trust_level varchar(255)                NOT NULL,
	uploader varchar(255)                   NULL,
	valid_from timestamptz(6)               NULL,
	valid_until timestamptz(6)              NULL,
	validator_bpn varchar(255)              NULL,
	validator_name varchar(255)             NULL,
	document_file_id int8                   NULL,
	certificate_type_id int8                NOT NULL,
	CONSTRAINT certificate_pkey PRIMARY KEY (id),
	CONSTRAINT certificate_trust_level_check CHECK (((trust_level)::text = ANY ((ARRAY['None'::character varying, 'Low'::character varying, 'Medium'::character varying, 'High'::character varying, 'Trusted'::character varying])::text[]))),
	CONSTRAINT uk87409p9ghnykje6s1jkoss8an UNIQUE (business_partner_number, certificate_type_id),
	CONSTRAINT uk_bahdgguibchqivd3361homjl3 UNIQUE (uuid),
	CONSTRAINT uk_ftaw2c970rhnnvei620m3ea17 UNIQUE (document_file_id),
	CONSTRAINT uk_sd5jn5fvtseb9g7tnd50mlsgv UNIQUE (document_id)
);


-- Certificate foreign keys

ALTER TABLE certificate ADD CONSTRAINT fk2ka5sxagrjukq1ur6d2ead5te FOREIGN KEY (document_file_id) REFERENCES document(id);
ALTER TABLE certificate ADD CONSTRAINT fkbd0ni938xijs7aeik8s5p8lp3 FOREIGN KEY (certificate_type_id) REFERENCES certificate_types(id);


-- Enclosed_sites foreign keys

ALTER TABLE enclosed_sites ADD CONSTRAINT fksvicrwh3g5mwwq1kyw1h4g7s3 FOREIGN KEY (certificate_id) REFERENCES certificate(id);