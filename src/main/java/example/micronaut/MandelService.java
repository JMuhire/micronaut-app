package example.micronaut;

import javax.inject.Singleton;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

@Singleton
public class MandelService {
    // Deliberately set a high limit to determine if a complex point is a
    // member of the mandelbrot set.  This makes the difference between
    // sequential and parallel execution observable when rendering
    final int limit = 1024 * 128;

    final double lr = -2.0;
    final double li = -1.0;
    final double ur = 1.0;
    final double ui = 1.0;

    final double h = ui - li;
    final double w = ur - lr;

    final int lines = 20;
    final int columns = (int) (w / h * lines * 2);

    final double stepr = w / columns;
    final double stepi = h / lines;

    public static void main(String[] args) {
        new MandelService().render();
    }

    public String render() {
        // Calculation of lines
        return IntStream.range(0, lines)
                // Calculate the imaginary c value
                .mapToDouble(l -> ui - (l * stepi))
                .mapToObj(this::renderLine)
                .collect(joining("\n", "|", "|"));
    }

    String renderLine(double ci) {
        // Sequential calculation of a line
        return IntStream.range(0, columns)
                // Calculate the real c value
                .mapToDouble(c -> lr + (c * stepr))
                // Calculate if (cr, ci) is a member of the set
                .mapToInt(cr -> isMemberOfMandelbrotSet(cr, ci))
                // Map to character
                .mapToObj(i -> i < (limit * 95 / 100) ? "*" : " ")
                // Join to represent line as a string
                .collect(joining());
    }

    //  z_n+1 = z_n^2 + c
    // returns 0 if a member or a number between 1 and limit, representing
    // the rate at which the function escapes to infinity
    int isMemberOfMandelbrotSet(double cr, double ci) {
        double zr = cr;
        double zi = ci;

        double zr2 = zr * zr;
        double zi2 = zi * zi;

        int i = limit;
        while (i > 0 && (zr2 + zi2) < 4.0) {
            i--;
            double _zr = zr2 - zi2 + cr;
            double _zi = 2.0 * zr * zi + ci;

            zr = _zr;
            zi = _zi;

            zr2 = zr * zr;
            zi2 = zi * zi;
        }
        return i;
    }
}
