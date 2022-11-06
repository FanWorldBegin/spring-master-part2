--修改table 添加外键， 添加class_id
ALTER TABLE `person`
    ADD COLUMN `class_id` int NULL AFTER `address_id`,
ADD CONSTRAINT `FK_CLASS_CLASS_ID` FOREIGN KEY (`class_id`)
REFERENCES `class`(`class_id`);