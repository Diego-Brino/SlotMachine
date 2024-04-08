package br.unaerp.vo;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Player implements Serializable {
    private String id;
    private Double amount;
}
