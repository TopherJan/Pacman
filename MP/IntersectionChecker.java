import javax.swing.JLabel;
import java.awt.geom.Area;
import java.awt.Rectangle;
import javax.swing.*;

public class IntersectionChecker{
		public boolean intersects(JLabel pakganernman, JLabel label){
			Rectangle rectB = label.getBounds();
			Rectangle result = SwingUtilities.computeIntersection(pakganernman.getX(), pakganernman.getY(), pakganernman.getWidth(), pakganernman.getHeight(), rectB);

			if(label.isDisplayable() == true)
			return (result.getWidth() > 0 && result.getHeight() > 0);

			else
			return false;
		}
}
