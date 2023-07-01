USE `planningpoker`;


DELETE FROM `team`;
INSERT INTO `team`
    (`id`, `name`)
VALUES
    (1, 'My Remote Team'),
    (2, 'Frontend Team'),
    (3, 'Backend Team');


DELETE FROM `user`;
INSERT INTO `user`
    (`id`,`team_id`, `username`, `active`, `role`)
VALUES
    (1, 1, 'Max', 0, 'MODERATOR'),
    (2, 1, 'John', 0, 'PARTICIPANT'),
    (3, 1, 'Luca', 0, 'SPECTATOR'),

    (4, 2, 'Noha', 0, 'MODERATOR'),
    (5, 2, 'Maria', 0, 'PARTICIPANT'),
    (6, 2, 'Tim', 0, 'SPECTATOR'),

    (7, 3, 'Sophia', 0, 'MODERATOR'),
    (8, 3, 'Peta', 0, 'PARTICIPANT'),
    (9, 3, 'Tom', 0, 'SPECTATOR');


DELETE FROM `team_member`;
INSERT INTO `team_member`
    (`team_id`, `user_id`, `list_index`)
VALUES
    (1, 1, 0),
    (1, 2, 1),
    (1, 3, 2),

    (2, 4, 0),
    (2, 5, 1),
    (2, 6, 2),

    (3, 7, 0),
    (3, 8, 1),
    (3, 9, 2);


DELETE FROM `deck`;
INSERT INTO `deck`
    (`id`, `name`)
VALUES
    (1, 'Standard'),
    (2, 'Fibonacci'),
    (3, 'T-Shirt');


DELETE FROM `card`;
INSERT INTO `card`
    (`id`, `label`, `value`, `front_text_color`, `back_text_color`, `background_color`, `hover_color`)
VALUES
    (1,'0', '0', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (2, '½', '½', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (3, '1', '1', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (4, '2', '2', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (5, '3', '3', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (6, '5', '5', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (7, '8', '8', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (8, '13', '13', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (9, '20', '20', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (10, '40', '40', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (11, '100', '100', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (12, '?', '?', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (13, '∞', '∞', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (14, '☕', '☕', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),

    (15, '1', '1', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (16, '2', '2', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (17, '3', '3', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (18, '5', '5', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (19, '8', '8', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (20, '13', '13', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (21, '21', '21', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (22, '34', '34', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (23, '55', '55', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (24, '89', '89', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (25, '?', '?', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (26, '∞', '∞', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (27, '☕', '☕', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),

    (28, 'XXS', 'XXS', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (29, 'XS', 'XS', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (30, 'S', 'S', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (31, 'M', 'M', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (32, 'L', 'L', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (33, 'XL', 'XL', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (34, 'XXL', 'XXL', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (35, '?', '?', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (36, '∞', '∞', '000000', 'DC3545', 'F8F9FA', 'FFF0C1'),
    (37, '☕', '☕', '000000', 'DC3545', 'F8F9FA', 'FFF0C1');


DELETE FROM `deck_card`;
INSERT INTO `deck_card`
    (`deck_id`, `card_id`, `list_index`)
VALUES
    (1, 1, 0),
    (1, 2, 1),
    (1, 3, 2),
    (1, 4, 3),
    (1, 5, 4),
    (1, 6, 5),
    (1, 7, 6),
    (1, 8, 7),
    (1, 9, 8),
    (1, 10, 9),
    (1, 11, 10),
    (1, 12, 11),
    (1, 13, 12),
    (1, 14, 13),

    (2, 15, 0),
    (2, 16, 1),
    (2, 17, 2),
    (2, 18, 3),
    (2, 19, 4),
    (2, 20, 5),
    (2, 21, 6),
    (2, 22, 7),
    (2, 23, 8),
    (2, 24, 9),
    (2, 25, 10),
    (2, 26, 11),
    (2, 27, 12),

    (3, 28, 0),
    (3, 29, 1),
    (3, 30, 2),
    (3, 31, 3),
    (3, 32, 4),
    (3, 33, 5),
    (3, 34, 6),
    (3, 35, 7),
    (3, 36, 8),
    (3, 37, 9);


DELETE FROM `backlog`;
INSERT INTO `backlog`
    (`id`, `name`)
VALUES
    (1, 'My Product Backlog'),
    (2, 'Sprint Backlog'),
    (3, 'Tasks');


DELETE FROM `backlog_item`;
INSERT INTO `backlog_item`
    (`id`, `backlog_id`, `list_index`, `number`, `title`, `description`, `estimation`, `priority`, `created_by`, `updated_by`)
VALUES
    (1, 1, 0, 'US007', 'Create Account', 'As an unauthorized User I want to create a new account', '3', '1', 1, 1),
    (2, 1, 1, 'US001', 'Login', 'As an unauthorized User I want to login with username and password', '1', '2', 1, 1),
    (3, 1, 2, 'US010', 'Logout', 'As an authorized User I want to logout', '1', '3', 1, 1),
    (4, 1, 3, 'US009', 'Reset Password', 'As an authorized User I want to be able to reset my password', '1', '4', 1, 1),
    (5, 1, 4, 'US002', 'List Items', 'As an authorized User I want to see the list of items so that I can select one', '3', '5', 1, 1),
    (6, 1, 5, 'US004', 'Add Item', 'As an authorized User I want to add a new item so that it appears in the list', '5', '6', 1, 1),
    (7, 1, 6, 'US003', 'Delete Item', 'As an authorized User I want to delete the selected item', '2', '7', 1, 1),
    (8, 1, 7, 'US005', 'Edit Item', 'As an authorized user I want to edit the selected item', '5', '8', 1, 1),
    (9, 1, 8, 'US006', 'Import Data', 'As an authorized user I want to be able to import data', '8', '9', 1, 1),
    (10, 1, 9, 'US008', 'Export Data', 'As an authorized user I want to be able to export data', '8', '10', 1, 1),

    (11, 2, 0, 'US007', 'Create Account', 'As an unauthorized User I want to create a new account', '3', '1', 1, 1),
    (12, 2, 1, 'US001', 'Login', 'As an unauthorized User I want to login with username and password', '1', '2', 1, 1),
    (13, 2, 2, 'US010', 'Logout', 'As an authorized User I want to logout', '', '3', 1, 1),
    (14, 2, 3, 'US009', 'Reset Password', 'As an authorized User I want to be able to reset my password', '', '4', 1, 1),
    (15, 2, 4, 'US002', 'List Items', 'As an authorized User I want to see the list of items so that I can select one', '', '5', 1, 1),
    (16, 2, 5, 'US004', 'Add Item', 'As an authorized User I want to add a new item so that it appears in the list', '', '6', 1, 1),
    (17, 2, 6, 'US003', 'Delete Item', 'As an authorized User I want to delete the selected item', '', '7', 1, 1),
    (18, 2, 7, 'US005', 'Edit Item', 'As an authorized user I want to edit the selected item', '', '8', 1, 1),
    (19, 2, 8, 'US006', 'Import Data', 'As an authorized user I want to be able to import data', '', '9', 1, 1),
    (20, 2, 9, 'US008', 'Export Data', 'As an authorized user I want to be able to export data', '', '10', 1, 1);


DELETE FROM `session`;
INSERT INTO `session`
    (`id`, `public_id`, `team_id`, `deck_id`, `backlog_id`, `created_by`)
VALUES
    (1, UUID(), 1, 1, 1, 1),
    (2, UUID(), 2, 2, 2, 1),
    (3, UUID(), 3, 3, 3, 1);


DELETE FROM `estimation_round`;
INSERT INTO `estimation_round`
    (`id`, `session_id`, `backlog_item_id`, `finished_at`)
VALUES
    (1, 1, 1, TIMESTAMPADD(MINUTE, 1, CURRENT_TIMESTAMP())),
    (2, 1, 2, TIMESTAMPADD(MINUTE, 1, CURRENT_TIMESTAMP())),
    (3, 1, 3, TIMESTAMPADD(MINUTE, 1, CURRENT_TIMESTAMP())),

    (4, 2, 11, TIMESTAMPADD(MINUTE, 1, CURRENT_TIMESTAMP())),
    (5, 2, 12, TIMESTAMPADD(MINUTE, 1, CURRENT_TIMESTAMP())),
    (6, 2, 13, TIMESTAMPADD(MINUTE, 1, CURRENT_TIMESTAMP()));


DELETE FROM `estimation`;
INSERT INTO `estimation`
    (`id`, `estimation_round_id`, `list_index`, `estimation_value`, `created_by`)
VALUES
    (1, 1, 0, '8', 1),
    (2, 1, 1, '3', 2),

    (3, 2, 0, '5', 1),
    (4, 2, 1, '3', 2),

    (5, 3, 0, '3', 1),
    (6, 3, 1, '3', 2),

    (7, 4, 0, '13', 4),
    (8, 4, 1, '5', 5),

    (9, 5, 0, '5', 4),
    (10, 5, 1, '8', 5),

    (11, 6, 0, '5', 4),
    (12, 6, 1, '5', 5);
