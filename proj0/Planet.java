public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double G = 6.67e-11;
    public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet b){
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }
    public double calcDistance(Planet d){
        return Math.pow(Math.pow(this.xxPos-d.xxPos, 2)+Math.pow(this.yyPos-d.yyPos, 2), 0.5);
    }       
    public double calcForceExertedBy(Planet f){
        return G*this.mass*f.mass/Math.pow(this.calcDistance(f), 2);
    }
    public double calcForceExertedByX(Planet fx){
        double dx = fx.xxPos - this.xxPos;
        return this.calcForceExertedBy(fx)*dx/this.calcDistance(fx);
    }
    public double calcForceExertedByY(Planet fy){
        double dy = fy.yyPos - this.yyPos;
        return this.calcForceExertedBy(fy)*dy/this.calcDistance(fy);
    }

    public double calcNetForceExertedByX(Planet[] nfx){
        double result = 0;
        for (Planet s : nfx) {
            if (s.equals(this))
                continue;
            result += this.calcForceExertedByX(s);
        }
        return result;
     }

    public double calcNetForceExertedByY(Planet[] nfy){
        double result = 0;
        for (Planet s : nfy) {
            if (s.equals(this))
                continue;
            result += this.calcForceExertedByY(s);
        }
        return result;
     }

    public void update(double dt, double fX, double fY){
        double ax = fX/this.mass;
        double ay = fY/this.mass;
        this.xxVel += dt*ax;
        this.yyVel += dt*ay;
        this.xxPos += dt*this.xxVel;
        this.yyPos += dt*this.yyVel;
    }

    public void draw() {
        String filename = this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, "/images/"+filename);
    }

}