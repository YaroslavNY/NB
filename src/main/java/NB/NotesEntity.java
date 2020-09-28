package NB;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "NOTES", schema = "PUBLIC", catalog = "TEST")

public class NotesEntity {
    private String name;
    private String text;

    public  NotesEntity(String name, String text){
        this.name = name;
        this.text = text;
    }
    protected  NotesEntity(){

    }

    @Id
    @Column(name = "NAME", nullable = false, length = 55)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "TEXT", nullable = false, length = 255)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotesEntity that = (NotesEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text);
    }
}
