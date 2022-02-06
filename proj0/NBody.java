public class NBody {
    public double mass, xxPos, xxVel, yyPos, yyVel;
    public String imgFileName;

    public static double readRadius (String filename) {
        In in = new In(filename);

        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();

        return secondItemInFile;

    }

    public static Planet[] readPlanets (String filename) {
        In in = new In(filename);
        int firstItemInFile = in.readInt();
        Planet allPlanets[] = new Planet[firstItemInFile];
        double secondItemInFile = in.readDouble();

        for (int i = 0; i != firstItemInFile; ++i){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();

            Planet p = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
            allPlanets[i] = p;
        }
        return allPlanets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet allPlanets[] = readPlanets(filename);

        double radius = readRadius(filename);

        String imageToDraw = "images/starfield.jpg";
        StdDraw.setScale(-radius, radius);

        StdDraw.clear();
        StdDraw.picture(0, 0, imageToDraw);

        for (int i = 0; i != allPlanets.length; ++i){
            allPlanets[i].draw();
        }

        StdDraw.enableDoubleBuffering();
        
        double time = 0;

        while (time != T){
            double xForces[] = new double[allPlanets.length];
            double yForces[] = new double[allPlanets.length];

            for (int i = 0; i != allPlanets.length; ++i){
                xForces[i] = allPlanets[i].calcNetForceExertedByX(allPlanets);
                yForces[i] = allPlanets[i].calcNetForceExertedByY(allPlanets);
            }

            for (int i = 0; i != allPlanets.length; ++i){
                allPlanets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, imageToDraw);
            for (int i = 0; i != allPlanets.length; ++i){
                allPlanets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

            time += dt;
        }
        StdOut.printf("%d\n", allPlanets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < allPlanets.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
                allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);   
        }
    }
}