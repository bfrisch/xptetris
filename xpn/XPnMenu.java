package xpn;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class XPnMenu extends javax.swing.JMenuBar {
	private static final long serialVersionUID = 7864881585934574143L;

	protected void processSelection (String selection) {}
		
	public JMenu makeMenu(JMenu menu, String[] items, char[] accell) {
		JMenu tempMenu = menu;
	   
		ActionListener printListener = new ActionListener(  ) {
			public void actionPerformed(ActionEvent event) {
				processSelection(event.getActionCommand());
			}
		};
	   
		for (int i = 0; i<items.length; i ++) {
			if (!items[i].equals("|")) {
				JMenuItem item = new JMenuItem(items[i], accell[i]);
				item.addActionListener(printListener);
				item.setAccelerator(KeyStroke.getKeyStroke(accell[i],
					Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
				tempMenu.add(item);
			}
			else {
				tempMenu.addSeparator();
			}
		}
		return tempMenu;
	}
   
	public JMenu makeRadioMenu(JMenu menu, String[] items, char[] accell) {
		JMenu tempMenu = menu;
	   
		ActionListener printListener = new ActionListener(  ) {
			public void actionPerformed(ActionEvent event) {
				processSelection(event.getActionCommand());
			}
		};
	   
		ButtonGroup buttonGroup = new ButtonGroup(  );
	   
		for (int i = 0; i<items.length; i ++) {
			if (!items[i].equals("|")) {
			   
				JMenuItem item = new JRadioButtonMenuItem(items[i]);
				buttonGroup.add(item);
			   item.addActionListener(printListener);
			   item.setAccelerator(KeyStroke.getKeyStroke(accell[i],
					   Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
			   if (i == 0) {item.setSelected(true);}
			   tempMenu.add(item);
		   }
		   else {
			   tempMenu.addSeparator();
		   }
	   }
	   return tempMenu;
   }
   
   public JMenu makeCheckMenu(JMenu menu, String[] items, char[] accell) {
	   JMenu tempMenu = menu;
	   
	   ActionListener printListener = new ActionListener(  ) {
		   public void actionPerformed(ActionEvent event) {
			   processSelection(event.getActionCommand());
		   }
	   };
	   
	   for (int i = 0; i<items.length; i ++) {
		   if (!items[i].equals("|")) {
			   
			   JMenuItem item = new JCheckBoxMenuItem(items[i]);
			   item.addActionListener(printListener);
			   item.setAccelerator(KeyStroke.getKeyStroke(accell[i],
					   Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
			   if (i == 0) {item.setSelected(true);}
			   tempMenu.add(item);
		   }
		   else {
			   tempMenu.addSeparator();
		   }
	   }
	   return tempMenu;
   }
}
