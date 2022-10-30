package pl.mojeprojecty.conferenceroomreservationsystem.example.shallowCopyAndDeepCopy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FootballLeague implements Cloneable{

    private String name;
    private String Country;
    private List<FootballTeam> listOfTeam;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        FootballLeague footballLeague = (FootballLeague) super.clone();//shallow copy
//        List<FootballTeam> footballTeams = (FootballTeam) this.getListOfTeam().clone();//shallow copy course = deepCopy
//        footballLeague.setListOfTeam(footballTeams);//zmieniamy liste na skopiowaną
        return footballLeague;
    }

    public static void main(String[] args) {
        List<FootballPlayer> footballPlayers = List.of(new FootballPlayer("Adam", "Nowak", 23, Position.DEFENDER));
        List<FootballTeam> footballTeams = List.of(new FootballTeam("Math", LocalDate.of(1922, 3, 19) , footballPlayers));
        FootballLeague footballLeague = new FootballLeague("Kartofliska", "Polska", footballTeams);

        try {
            FootballLeague footballLeagueCopy = (FootballLeague) footballLeague.clone();
            System.out.println("League: " + footballLeague);
            System.out.println("League copy: " + footballLeagueCopy);

            //change age
            System.out.println("Change name: ");
            footballLeagueCopy.setName("Ekstraklasa");
            System.out.println("League: " + footballLeague);
            System.out.println("League copy: " + footballLeagueCopy);

            //change team
            System.out.println("Change list of teams: ");
            footballLeague.getListOfTeam()
                    .add(new FootballTeam("", LocalDate.now(), List.of(null)));
            System.out.println("League: " + footballLeague);
            System.out.println("League copy: " + footballLeagueCopy);

            //change player
            System.out.println("Change player: ");
            footballLeague.getListOfTeam().get(0).getListOfPlayer()
                    .add(new FootballPlayer("","",35, Position.GOALKEEPER));
            System.out.println("League: " + footballLeague);
            System.out.println("League copy: " + footballLeagueCopy);

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

@Setter
@Getter
@ToString
@AllArgsConstructor
class FootballTeam implements Cloneable {
    private String name;
    private LocalDate createDate;
    private List<FootballPlayer> listOfPlayer;


    @Override
    public Object clone() throws CloneNotSupportedException {
        FootballTeam footballTeam = (FootballTeam) super.clone();
//        List<FootballPlayer> footballPlayers = (FootballPlayer) this.getListOfPlayer().clone();
        return super.clone();
    }
}

@Setter
@Getter
@ToString
@AllArgsConstructor
class FootballPlayer implements Cloneable{
    private String name;
    private String surname;
    private int age;
    private Position position;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}



enum Position{
    FORWARD,
    MIDFIELDER,
    DEFENDER,
    GOALKEEPER
}