CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, 
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_ADMIN', 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE exhibits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    default_image_url VARCHAR(255), 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE exhibit_translations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exhibit_id BIGINT NOT NULL,
    language_code VARCHAR(10) NOT NULL, 
    title VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY (exhibit_id) REFERENCES exhibits(id) ON DELETE CASCADE,
    UNIQUE (exhibit_id, language_code) 
);

CREATE TABLE qr_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code_string VARCHAR(100) NOT NULL UNIQUE, 
    exhibit_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exhibit_id) REFERENCES exhibits(id) ON DELETE CASCADE
);

CREATE TABLE media_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exhibit_id BIGINT NOT NULL,
    language_code VARCHAR(10) NOT NULL, 
    audio_url VARCHAR(255) NOT NULL, 
    transcript TEXT, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exhibit_id) REFERENCES exhibits(id) ON DELETE CASCADE,
    UNIQUE (exhibit_id, language_code)
);
