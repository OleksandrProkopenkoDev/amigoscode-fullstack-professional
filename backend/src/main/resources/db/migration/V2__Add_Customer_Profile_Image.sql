ALTER TABLE customer
ADD COLUMN profile_image_id VARCHAR(36);

ALTER table customer
add constraint profile_image_id_unique
unique (profile_image_id);