package ht.com.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.data.annotatcion.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Document(collation = "users")
public class Profile {
//    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String otherInfo;
}
