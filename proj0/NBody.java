public class NBody{
    public static double readRadius(String filename){
        In in = new In(filename);
        int nothing = in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Planet[] readPlanets(String filename){ 
        In in = new In(filename);
        int n = in.readInt();
        double radius = in.readDouble();
        Planet[] universe = new Planet[n];
        for (int i = 0; i < n; i += 1) {                 
           
            /*double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mas = in.readDouble();
            String img = in.readString();
            Body planet = new Body(xPos, yPos, xVel, yVel, mas,img);
           */

            Planet planet = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());                    
            universe[i] = planet;
        }
        return  universe;
    }
    public static void main(String[] args){
            double T = Double.parseDouble(args[0]);
            double dt = Double.parseDouble(args[1]);
            String filename = args[2];
            double radius = NBody.readRadius(filename);
            Planet[] body = NBody.readPlanets(filename);

            StdDraw.setXscale(-radius, radius);
            StdDraw.setYscale(-radius, radius);
            StdDraw.picture(0, 0, "images/starfield.jpg", 2*radius, 2*radius);
            for (Planet planet : body) {
                planet.draw();
            }

            StdDraw.enableDoubleBuffering();

            double time = 0;
            while (time <= T) {
                double[] xForcesarray = new double[body.length];
                double[] yForcesarray = new double[body.length];
                
                    for (int i = 0; i < body.length; i +=1 ) {
                        xForcesarray[i] =  body[i].calcNetForceExertedByX(body);
                        yForcesarray[i] =  body[i].calcNetForceExertedByY(body);
                    }
                    for (int i = 0; i < body.length; i +=1 ) {
                        body[i].update(dt, xForcesarray[i], yForcesarray[i]);
                    } 
                    StdDraw.picture(0, 0, "images/starfield.jpg", 2*radius, 2*radius);
                    for (Planet planet : body) {
                        planet.draw();
                    }
                    StdDraw.show();
                    StdDraw.pause(10);
                    time += dt;
                }
            StdOut.printf("%d\n", body.length);
            StdOut.printf("%.2e\n", radius);
            for (int i = 0; i < body.length; i++) {
                 StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                      body[i].xxPos, body[i].yyPos, body[i].xxVel,
                      body[i].yyVel, body[i].mass, body[i].imgFileName);   
            }

    }
		

}