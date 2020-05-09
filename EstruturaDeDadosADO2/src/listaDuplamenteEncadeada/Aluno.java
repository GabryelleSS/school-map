package listaDuplamenteEncadeada;

public class Aluno {
    private String nome;
    private int RA;
    private String curso;
    private float nota;
    private float media;

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRA() {
        return RA;
    }
    
    public void setRA(int RA) {
        this.RA = RA;
    }

    public String getCurso() {
        return curso;
    }
    
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public float getNota() {
        return nota;
    }
    
    public void setCurso(float nota) {
        this.nota = nota;
    }
   
//Construtores:
    public Aluno(){ }
   
    public Aluno(String nome, int RA, String curso, float nota, float media){
        this.nome = nome;
        this.RA = RA;
        this.curso = curso;
        this.nota = nota;
        this.media = media;   
    }
}
