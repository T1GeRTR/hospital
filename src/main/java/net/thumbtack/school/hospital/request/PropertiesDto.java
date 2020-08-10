package net.thumbtack.school.hospital.request;

import org.springframework.beans.factory.annotation.Value;

public class PropertiesDto {
    @Value("${max_name_length}")
    public static int max_name_length;

    @Value("${min_password_length}")
    public static int min_password_length;
}
