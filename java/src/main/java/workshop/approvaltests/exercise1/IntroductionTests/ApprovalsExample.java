package workshop.approvaltests.exercise1.IntroductionTests;

public class ApprovalsExample {
    public static String mergeNameAndAge(String name, int age){
        String title = "";
        if(age <= 18){
            title = "Junior";
        }
        if(age > 18 && age <= 60){
            title = "Mister";
        }
        if(age > 60){
            title = "Senior";
        }
        return title + " " + name + ", " + age + " years";
    }
}
