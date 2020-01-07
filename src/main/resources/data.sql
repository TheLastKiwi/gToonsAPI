@PostMapping("/seed")
public boolean seed(){
    User u;
    for(int i = 0; i < 2; i++) {
        u = User.builder().username("user" + i).email("email@" + i + ".com").passwordHash(passwordEncoder.encode("password" + i)).build();
        userRepository.save(u);
    }

    UserDeckCard udc;
    for(int i = 1; i <= 12; i++){
        udc = UserDeckCard.builder().cardId(i).userId(1).build();
        userDeckCardRepository.save(udc);
        udc = UserDeckCard.builder().cardId(i+20).userId(2).build();
        userDeckCardRepository.save(udc);
    }
    return true;
}

id username email passwordHash
INSERT into user VALUES
(1,"username0","myemail@0","$2a$10$.L737oGz1h2c95IgHLcQ3OS9ny4aWrFqaCPsOThB8jm2lbKgGM3WW"),
(2,"username1","myemail@1","$2a$10$/IDETaHQVniYZhkiOytQguEdjJth0xRftjEsIcDmTGKzSigfHDQFC"),
(3,"username2","email@2.com","$2a$10$trnq39O7w235lP5zJ6RC5u/ny/34PQKC6VjN7DkFZoVzE.cxKXJxS")

id userid cardid
INSERT INTO user_deck VALUES
(1,1,1),
(2,1,2),
(3,1,3),
(4,1,4),
(5,1,5),
(6,1,6),
(7,1,7),
(8,1,8),
(9,1,9),
(10,1,10),
(11,1,11),
(12,1,12),
(13,1,13),
(14,1,14),
(15,1,15),
(16,1,16),
(17,1,17),
(18,1,18),
(19,1,19),
(20,1,20),
(21,1,21),
(22,1,22),
(23,1,23),
(24,1,24);

INSERT INTO user_collection VALUES
(1,1,1),
(2,1,2),
(3,1,3),
(4,1,4),
(5,1,5),
(6,1,6),
(7,1,7),
(8,1,8),
(9,1,9),
(10,1,10),
(11,1,11),
(12,1,12),
(13,1,13),
(14,1,14),
(15,1,15),
(16,1,16),
(17,1,17),
(18,1,18),
(19,1,19),
(20,1,20),
(21,1,21),
(22,1,22),
(23,1,23),
(24,1,24);
