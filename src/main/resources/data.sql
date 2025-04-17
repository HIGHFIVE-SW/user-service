INSERT INTO tier(tier_id, tier_name, required_exp, tier_image_url)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), '초보 여행자', 0, 'noviceTravelerUrl'),
       (UNHEX(REPLACE(UUID(), '-', '')), '프로 탐험가', 50, 'proTravelerUrl'),
       (UNHEX(REPLACE(UUID(), '-', '')), '글로벌 마스터', 150, 'GlobalMasterUrl'),
       (UNHEX(REPLACE(UUID(), '-', '')), '유니버스 리더', 300, 'UniverseLeaderUrl');
