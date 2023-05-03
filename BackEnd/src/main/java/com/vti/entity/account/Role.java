package com.vti.entity.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "RoleId")
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "RoleName", unique = true)
  private ERole name;

//  @ManyToMany(fetch = FetchType.LAZY,
//          cascade = {
//                  CascadeType.PERSIST,
//                  CascadeType.MERGE
//          },
//          mappedBy = "roles")
//  @JsonIgnore
//  private List<Account> accounts;
}