package com.example.StockDiplom.models;

import com.example.StockDiplom.models.Enums.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user" , schema = "stockcopy")
public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq")
        @SequenceGenerator(name="entity_seq", sequenceName="entity_seq", allocationSize = 1)
        private Long id;
        @Column(name = "username")
        private String username;
        @Column(name = "password")
        private String password;
        @Column(name="name")
        private String name;
        @Column(name="surname")
        private String surname;
        @Column(name="phone")
        private String phone;
        @Column(name="email")
        private String email;
        @Column(name = "active")
        private boolean active;

        @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
        @CollectionTable(name = "user_role", joinColumns = @JoinColumn (name = "user_id"))
        @Enumerated(EnumType.STRING)
        private Set<Role> roles;

        /*@OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        private List<Trade> trade;*/

        @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private Set<Trade> tradesAsSeller;

        @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private Set<Trade> tradesAsBuyer;

        @OneToMany(mappedBy="user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
        private List<Stuff> stuffList ;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
        private List<Member> memberUser;

        public boolean isActive() {return active;}
        public void setActive(boolean active) {this.active = active;}

        public Set<Role> getRoles() {return roles;}
        public void setRoles(Set<Role> roles) {this.roles = roles;}

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return getRoles();
        }

        @Override
        public String getUsername() {return username;}
        @Override
        public boolean isAccountNonExpired() {return true;}
        @Override
        public boolean isAccountNonLocked() {return true;}
        @Override
        public boolean isCredentialsNonExpired() {return true;}
        @Override
        public boolean isEnabled() {return isActive();}

        @Override
        public String toString() {
                return String.format(
                        "User[id=%d, name='%s', surname='%s']",
                        id, name, surname);
        }

    }