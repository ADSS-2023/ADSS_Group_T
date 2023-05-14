package DataLayer.Util;

public class DogDto extends DTO{
    private String name;
    private String breed;
    private int age;
    public DogDto(String tableName,String name,String breed,int age){
        super(tableName);
        this.name = name;
        this.breed = breed;
        this.age = age;
    }
}
