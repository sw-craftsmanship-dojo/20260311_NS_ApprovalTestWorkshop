export function mergeNameAndAge(name: string, age: number): string {
    let title = "";
    if (age <= 18) {
        title = "Junior";
    }
    if (age > 19 && age <= 60) {
        title = "Mister";
    }
    if (age > 60) {
        title = "Senior";
    }
    return title + " " + name + ", " + age + " years";
}
