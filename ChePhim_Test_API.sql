DROP DATABASE IF EXISTS ChePhim;
CREATE DATABASE ChePhim;
USE ChePhim;

DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles(
                      RoleId     SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      RoleName 	ENUM ('ADMIN', 'MANAGER','USER') UNIQUE
);
INSERT INTO `chephim`.`Roles` (RoleId, RoleName) VALUES ('1', 'ADMIN');
INSERT INTO `chephim`.`Roles` (RoleId, RoleName) VALUES ('2', 'MANAGER');
INSERT INTO `chephim`.`Roles` (RoleId, RoleName) VALUES ('3', 'USER');



DROP TABLE IF EXISTS `Account`;
CREATE TABLE `Account`(
                          accountID            SMALLINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          email                VARCHAR(50) NOT NULL UNIQUE KEY,
                          username             VARCHAR(50) NOT NULL UNIQUE KEY,
                          CreateDate           DATETIME DEFAULT NOW(),
                          url_avatar           VARCHAR(100),
                          blockExpDate		DATETIME,
                          `password`           VARCHAR(100),
                          status int
);

CREATE TABLE IF NOT EXISTS Film(
                                   id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                   title VARCHAR(150) NOT NULL,
                                   vn_title VARCHAR(150) NOT NULL,
                                   story_line TEXT NOT NULL,
                                   released_at DATETIME,
                                   duration SMALLINT NOT NULL,
                                   poster_path			TEXT,
                                   `type` ENUM('MOVIE', 'SERIES') NOT NULL,
                                   gross BIGINT,
                                   seasons TINYINT,
                                   episodes INT
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS Film_Photo(
                                         id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                         film_id BIGINT NOT NULL,
                                         path_name TEXT NOT NULL,
                                         type_photo varchar(10),
                                         FOREIGN KEY (film_id) REFERENCES Film(id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*============================== INSERT DATABASE =======================================*/
/*======================================================================================*/

-- Add data Account
INSERT INTO `Account`(Email						, Username			, CreateDate  				,`password`													)
VALUES
('admin@gmail.com'				, 'admin'			,'2022-05-19' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi' 	),
('Email1@gmail.com'				, 'Username1'	 	,'2021-03-05' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi' 	),
('Email2@gmail.com'				, 'Username2'	 	,'2020-06-05' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	),
('Email3@gmail.com'			, 'Username3'	 	,'2019-06-07' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	),
('Email4@gmail.com'			, 'Username4'	 	,'2018-03-04' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	),
('Email5@gmail.com'			, 'Username5'	 	,'2020-02-10' ,		    '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	),
('Email6@gmail.com'				, 'Username6'	 	,'2017-02-11' ,		    '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	);

-- INSERT INTO `Account`(email							, username		, CreateDate  				,`password`														)
-- VALUES
-- 					('admin@gmail.com'				, 'admin'		,'2022-05-19' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi' 		),
-- 					('Email1@gmail.com'				, 'Username1'  	,'2021-03-05' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi' 		),
-- 					('Email2@gmail.com'				, 'Username2'	,'2020-06-05' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		),
--                     ('Email3@gmail.com'				, 'Username3'	,'2019-06-07' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		),
--                     ('Email4@gmail.com'				, 'Username4'	,'2018-03-04' ,			'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		),
--                     ('Email5@gmail.com'				, 'Username5'	,'2020-02-10' ,		    '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		),
--                     ('Email6@gmail.com'				, 'Username6'	,'2017-02-11' ,		    '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		);

CREATE TABLE account_roles(
                              account_ID 	SMALLINT ,
                              role_ID     SMALLINT UNSIGNED  ,
                              PRIMARY KEY(role_ID,account_ID),
                              FOREIGN KEY (account_ID)  REFERENCES `Account`(accountID),
                              FOREIGN KEY (role_ID)  REFERENCES `Roles`(RoleId)
);
INSERT INTO account_roles(account_ID,	role_ID)
VALUES					 (1			,		1),
                           (1			,		2),
                           (1			,		3),
                           (2			,		3),
                           (3			,		2),
                           (2			,		2);

INSERT INTO Film (title, vn_title, story_line, released_at, duration, poster_path, `type`, gross, seasons, episodes) VALUES
('Shawshank Redemption', 'Nhà Tù Shawshank', 'Andrew, một nhân viên nhà băng, bị kết án chung thân sau khi giết vợ và nhân tình của cô.
Anh một mực cho rằng mình bị oan. Andy bị đưa tới nhà tù Shawshank. Tại đây, thế giới ngầm của các phạm nhân, sự hà khắc của hệ thống quản giáo
xung đột và giành nhau quyền thống trị. Chỉ có các phạm nhân trung lập là bị kẹt ở giữa và có thể bỏ mạng. Làm quen với tay quản lý chợ đen
- Red, Andy dần thích nghi với cuộc sống tại Shawshank. Song, kế hoạch lớn hơn việc tồn tại ở nhà tù này đang được anh suy tính.',
 '1994-09-10', '142','', 'movie', '28884504', NULL, NULL),
('The Queen Gambit', 'Gambit Hậu', 'Ở một cô nhi viện những năm 1950, một cô gái trẻ bộc lộ tài năng cờ vua đáng kinh ngạc và bắt đầu hành
trình đến đỉnh vinh quang trong khi vật lộn với thói nghiện ngập.', '2020-10-23', '60', '', 'series', NULL, '1', '7'),
('Blade Runner 2049', 'Tội Phạm Nhân Bản 2049', 'Trong một nhiệm vụ, viên cảnh sát K - vốn là một người máy đời mới - phát hiện thi thể của
một rô-bốt nữ đã qua đời khi mổ sinh con nhiều năm trước. Để ngăn chặn một cuộc chiến tranh giữa các rô-bốt nhân bản với con người, K được
bí mật giao nhiệm vụ tìm đứa trẻ và phá hủy tất cả các bằng chứng liên quan đến nó.', '2017-10-05', '164', '', 'movie', '259352064', NULL, NULL),
('Django Unchained', 'Hành Trình Django', 'Với sự giúp đỡ của một thợ săn tiền thưởng người Đức, một nô lệ được tự do lên đường giải cứu
vợ mình khỏi một chủ đồn điền tàn bạo ở Mississippi.', '2012-12-11', '165', '', 'movie', '426074373', NULL, NULL),
('Good Omens', 'Thiện Báo', 'bộ phim kể về ác quỷ Crowley (David Tennant) và thiên thần Aziraphale (Michael Sheen), những người bạn lâu năm, đã
quen với cuộc sống trên Trái đất, tìm cách ngăn chặn Antichrist đến và cùng với nó là Armageddon, trận chiến cuối cùng giữa Thiên đường và Địa
ngục', '2019-05-31', '55', '', 'series', NULL, '1', '6'),
('Inception', 'Kẻ Đánh Cắp Giấc Mơ', 'Một tên trộm đánh cắp bí mật của công ty thông qua việc sử dụng công nghệ chia sẻ giấc mơ được giao
nhiệm vụ ngược lại là gieo một ý tưởng vào tâm trí của một C.E.O.', '2010-07-08', '148', '', 'movie', '836848102', NULL, NULL),
('The Sandman', 'Người Cát', 'Sau nhiều năm bị giam cầm, Morpheus – Chúa tể Cõi Mộng – bắt đầu cuộc hành trình xuyên qua các thế giới để tìm
lại những thứ đã bị cướp đoạt và khôi phục sức mạnh.', '2022-08-05', '45', '', 'series', NULL, '1', '11'),
('The Dark Knight', 'Kỵ Sĩ Bóng Đêm', 'The Dark Knight là phần tiếp theo của Batman Begins kể về thành phố Gotham bị đảo lộn do hàng loạt vụ
giết người xảy ra mà không tìm ra hung thủ.', '2008-07-18', '152', '', 'movie', '1006102277', NULL, NULL),
('The Godfather', 'Bố Già', 'Bố già (The Godfather) là một bộ phim hình sự sản xuất năm 1972 dựa theo tiểu thuyết cùng tên của nhà văn Mario
Puzo và do Francis Ford Coppola đạo diễn. Phim xoay quanh diễn biến của gia đình mafia gốc Ý Corleone trong khoảng 10 năm từ 1945 đến 1955. ',
 '1972-03-24', '175', '', 'movie', '250341816', NULL, NULL),
('The Haunting of Bly Manor', 'Chuyện Ma Ám Ở Trang Viên Bly', 'Chết không có nghĩa là biến mất. Một cô gia sư bị cuốn vào những bí mật rùng
rợn trong phim lãng mạn kiểu gothic này, từ tác giả của "Chuyện ma ám ở căn nhà họ Hill".', '2020-10-09', '50', '', 'series', NULL, '1', '9');