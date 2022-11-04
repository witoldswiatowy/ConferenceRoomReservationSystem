package pl.mojeprojecty.conferenceroomreservationsystem.example.prowadzacy.copy;

/*
Na podstawie poniższej struktury:
FootballLeague -> FootballTeam -> FootballPlayer zaprezentuj działanie mechanizmu klonowania w javie: deepcopy i shallowcopy

class FootballLeague: nazwa, lista zespołów, kraj
class FootballTeam: nazwa, lista zawodników, rok powstania
class FootballPlayer: imię, nazwisko, wiek, pozycja na boisku (enum)

 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        FootballPlayer footballPlayer = new FootballPlayer("Cristiano", "Ronaldo", 34, Position.STRIKER);
        FootballTeam footballTeam = new FootballTeam("Manchaster Unites", 2000, Arrays.asList(footballPlayer));
        FootballLeague footballLeague = new FootballLeague("Premier League", "England", Arrays.asList(footballTeam));

        try {
            FootballLeague footballLeagueCopy = (FootballLeague) footballLeague.clone();
            System.out.println("Football league: " + footballLeague);
            System.out.println("Football league original: " + footballLeagueCopy);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}


class FootballLeague implements Cloneable {

    private String name;
    private String country;
    private List<FootballTeam> footballTeams;

    public FootballLeague(String name, String country, List<FootballTeam> footballTeams) {
        this.name = name;
        this.country = country;
        this.footballTeams = footballTeams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<FootballTeam> getFootballTeams() {
        return footballTeams;
    }

    public void setFootballTeams(List<FootballTeam> footballTeams) {
        this.footballTeams = footballTeams;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        FootballLeague footballLeagueCopy = (FootballLeague) super.clone();
        List<FootballTeam> footballTeamsCopy = footballLeagueCopy.footballTeams.stream().map(ft -> {
            try {
                return (FootballTeam) ft.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        footballLeagueCopy.setFootballTeams(footballTeamsCopy);
        return footballLeagueCopy;
    }

    @Override
    public String toString() {
        return "FootballLeague{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", footballTeams=" + footballTeams +
                '}';
    }
}

class FootballTeam implements Cloneable {
    private String name;
    private int yearOfCreation;
    private List<FootballPlayer> footballPlayers;

    public FootballTeam(String name, int yearOfCreation, List<FootballPlayer> footballPlayers) {
        this.name = name;
        this.yearOfCreation = yearOfCreation;
        this.footballPlayers = footballPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public List<FootballPlayer> getFootballPlayers() {
        return footballPlayers;
    }

    public void setFootballPlayers(List<FootballPlayer> footballPlayers) {
        this.footballPlayers = footballPlayers;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        FootballTeam footballTeamCopy = (FootballTeam) super.clone();
        List<FootballPlayer> footballPlayerListCopy = footballTeamCopy.getFootballPlayers().stream().map(p->{
            try {
                return (FootballPlayer) p.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        footballTeamCopy.setFootballPlayers(footballPlayerListCopy);
        return footballTeamCopy;
    }

    @Override
    public String toString() {
        return "FootballTeam{" +
                "name='" + name + '\'' +
                ", yearOfCreation=" + yearOfCreation +
                ", footballPlayers=" + footballPlayers +
                '}';
    }
}

class FootballPlayer implements Cloneable {
    private String name;
    private String lastName;
    private int age;
    private Position position;

    public FootballPlayer(String name, String lastName, int age, Position position) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "FootballPlayer{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", position=" + position +
                '}';
    }
}

enum Position {
    STRIKER, DEFENDER
}