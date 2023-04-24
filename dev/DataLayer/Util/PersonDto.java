package DataLayer.Util;

public class PersonDto extends DTO {

    private String name;
    private int age;
    public PersonDto(String tableName,String name,int age){
        super(tableName);
        this.name=name;
        this.age=age;
    }

/*    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }*/
}
