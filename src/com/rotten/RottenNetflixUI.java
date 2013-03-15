package com.rotten;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RottenNetflixUI extends JFrame implements ActionListener{
	
	TextField movieInput;
	JButton button;
	JPanel matchingResults;
	JPanel netflixResults;
	JPanel rottenTomatoResults;
	Label matchingResultsLabel;
	Label rottenTomatoLabel;
	Label netflixLabel;
	
	ResultsHolder results;
	
	public RottenNetflixUI(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		movieInput = new TextField();
		
		button = new JButton("Search");
		button.addActionListener(this);
		
		matchingResults =  new JPanel(new GridLayout(0,1));
		netflixResults = new JPanel(new GridLayout(0,1));
		rottenTomatoResults = new JPanel(new GridLayout(0,1));
		
		matchingResultsLabel = new Label("Matching Results");
		netflixLabel = new Label("Netflix Results");
		rottenTomatoLabel = new Label("Rotten Tomatoes Results");
		
		results = new ResultsHolder();
		
		init();
	}
	
	public void init(){
		
		this.setLayout(null);		
		this.setSize(750,800);
		this.setTitle("Rotten Netflix");
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("freshImg.png"));
		
		this.setIconImage(image	);
		
		movieInput.setBounds(10, 10, 200, 20);
		movieInput.setVisible(true);
		add(movieInput);
		
		button.setBounds(220, 10, 100, 20);
		button.setToolTipText("Search for movies");
		
		this.getRootPane().setDefaultButton(button);
		
		add(button);
		
		matchingResultsLabel.setBounds(10, 40, 100, 20);
		add(matchingResultsLabel);
		matchingResults.setBounds(10, 70, 700, 200);
		matchingResults.setBackground(Color.WHITE);
		add(matchingResults);
		
		netflixLabel.setBounds(10, 280, 100, 20);
		add(netflixLabel);
		netflixResults.setBounds(10, 300, 700, 200);
		netflixResults.setBackground(Color.WHITE);
		add(netflixResults);
		
		rottenTomatoLabel.setBounds(10, 510, 150, 20);
		add(rottenTomatoLabel);
		rottenTomatoResults.setBounds(10, 530, 700, 200);
		rottenTomatoResults.setBackground(Color.WHITE);
		add(rottenTomatoResults);
	}
	
	private JLabel getResultLabel(String s, String tooltip){
		JLabel label = new JLabel(s);
		label.setSize(800, 300);
		label.setVisible(true);
		label.setLayout(null);
		label.setToolTipText(tooltip);
		
		return label;
	}
	
	private JLabel getResultLabel(String s, String tooltip, boolean fresh){
		JLabel label = getResultLabel(s, tooltip);
		if(fresh){
			label.setIcon(new ImageIcon(getClass().getResource("freshImg.png")));
		}
		else{
			label.setIcon(new ImageIcon(getClass().getResource("rottenImg.png")));
		}

		return label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(button) || e.getSource().equals(movieInput)){
			String input = movieInput.getText();
			results.doWork(input);
			
			matchingResults.removeAll();
			matchingResults.revalidate();
			matchingResults.repaint();
			netflixResults.removeAll();
			netflixResults.revalidate();
			netflixResults.repaint();
			rottenTomatoResults.removeAll();
			rottenTomatoResults.revalidate();
			rottenTomatoResults.repaint();
			
			matchingResults.setLayout(new GridLayout(0,1));
			netflixResults.setLayout(new GridLayout(0,1));
			rottenTomatoResults.setLayout(new GridLayout(0,1));

			int count=1;
			for(CompositeResult c : results.getExactMatches()){
				if(count++ > 6) break;
				matchingResults.add(getResultLabel(c.toString(), c.castToString(), c.isFresh()));
			}
			matchingResults.revalidate();
			
			for(CompositeResult c : results.getPartialMatches()){
				if(count++ > 6) break;
				matchingResults.add(getResultLabel(c.toString(), c.castToString(), c.isFresh()));
			}
			matchingResults.revalidate();
			
			count = 1;
			for(NetflixResult r : results.getNfResults()){
				if(count++ > 6) break;
				netflixResults.add(getResultLabel(r.toString(), "Cast information not available from Netflix"));
			}
			netflixResults.revalidate();
			
			count = 1;
			for(RottenTomatoResult r : results.getRtResults()){
				if(count++ > 4) break;
				rottenTomatoResults.add(getResultLabel(r.toString(), r.castToString(), r.isFresh()));
			}
			rottenTomatoResults.revalidate();
			
		}
		
	}
	
	public static void main(String[] args){
		RottenNetflixUI ui = new RottenNetflixUI();
		ui.setVisible(true);
	}
}
