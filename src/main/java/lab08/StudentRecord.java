package lab08;

public class StudentRecord {
    private String studentid;
    private float assignments;
    private float midterm;
    private float finalExam;
    private float finalMark;
    private Character letterGrade;

    public StudentRecord(String studentid, float assignments, float midterm, float finalExam) {
        this.studentid = studentid;
        this.assignments = assignments;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.finalMark = (assignments * 0.2f) + (midterm * 0.3f) + (finalExam * 0.5f);

        if (finalMark >= 80 && finalMark <= 100) {
            this.letterGrade = 'A';
        } else if (finalMark >= 70 && finalMark <= 79 ) {
            this.letterGrade = 'B';
        } else if (finalMark >= 60 && finalMark <= 69 ) {
            this.letterGrade = 'C';
        } else if (finalMark >= 50 && finalMark <= 59 ) {
            this.letterGrade = 'D';
        } else {
            this.letterGrade = 'F';
        }
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public float getMidterm() {
        return midterm;
    }

    public void setMidterm(float midterm) {
        this.midterm = midterm;
    }

    public float getAssignments() {
        return assignments;
    }

    public void setAssignments(float assignments) {
        this.assignments = assignments;
    }

    public float getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(float finalExam) {
        this.finalExam = finalExam;
    }

    public float getFinalMark() {
        return finalMark;
    }

    public Character getLetterGrade() {
        return letterGrade;
    }
}
