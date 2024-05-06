package dev.nikhil.userservice.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Role extends BaseModel{
    private String role;
}
