package microservices.book.socialmultiplications.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@RequiredArgsConstructor

@ToString
@EqualsAndHashCode
@Entity
public final class Multiplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MULTIPLICATION_ID")
    private Long id;
    private final int factorA;
    private final  int factorB;

    protected  Multiplication(){

        this(0,0);
    }



}

