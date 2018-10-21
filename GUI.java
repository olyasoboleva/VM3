import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class GUI {
    static int n=0 ,f=0;
    static int a=0 ,b=0;
    static MathFunction function;
    private static class InputGUI extends JFrame{
        JRadioButton funcSin, funcSqrt, funcExp, funcSqr;
        JSpinner sMin, sMax, sDotsNum;
        InputGUI() {
            super("Метод трапеций");
            this.setBounds(100, 100, 420, 500);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);

            Font font = new Font("Calibri Light", Font.PLAIN, 16);
            Font titleFont = new Font("Calibri Light", Font.BOLD, 18);
            JButton bCheck = new JButton("Интерполировать");
            bCheck.setSize(new Dimension(100,40));
            JButton bCount = new JButton("Вычислить");
            bCount.setSize(new Dimension(100,40));
            bCheck.setFont(font);
            ButtonGroup functions = new ButtonGroup();
            funcSin = new JRadioButton("y = sin(x)", true); funcSin.setFont(font);
            functions.add(funcSin);
            funcSqrt = new JRadioButton("y = sqrt(x)", false); funcSqrt.setFont(font);
            functions.add(funcSqrt);
            funcSqr = new JRadioButton("y = sqr(x)", false); funcSqr.setFont(font);
            functions.add(funcSqr);
            funcExp = new JRadioButton("y = exp(x)", false); funcExp.setFont(font);
            functions.add(funcExp);
            SpinnerNumberModel mMin = new SpinnerNumberModel(0, -1000, 1000, 1);
            sMin = new JSpinner(mMin);
            SpinnerNumberModel mMax = new SpinnerNumberModel(0, -1000, 1000, 1);
            sMax = new JSpinner(mMax);
            SpinnerNumberModel dotsNum = new SpinnerNumberModel(0, -1000, 1000, 1);
            sDotsNum = new JSpinner(dotsNum);
            SpinnerNumberModel xValue = new SpinnerNumberModel(0, -1000, 1000, 0.1);
            JSpinner sX = new JSpinner(xValue);
            sX.setSize(new Dimension(300,30));
            JLabel func = new JLabel("Функции");func.setFont(titleFont);
            JLabel lDots = new JLabel("Количество точек");lDots.setFont(titleFont);
            JLabel lims = new JLabel("Интервал");lims.setFont(titleFont);
            JLabel result = new JLabel("Y");result.setFont(titleFont);
            JLabel lFrom = new JLabel("От");
            JLabel lTo = new JLabel("До");
            JLabel lX = new JLabel("X");
            JLabel lY = new JLabel("Y");
            JTextArea fieldResult = new JTextArea(1, 16);
            fieldResult.setBorder(new BevelBorder(1));
            fieldResult.setEditable(false);

            GridBagLayout gb = new GridBagLayout();
            this.setLayout(gb);
            GridBagConstraints c = new GridBagConstraints();

            bCheck.addActionListener(ev -> {
                MathFunction iFunc;
                initParams();
                if (a!=b && n!=0) {
                    LagrangePolynomial polynomial = new LagrangePolynomial(function, a, b, n);
                    iFunc = polynomial.interpolate();
                    double[] xData = new double[n + 6], yData1 = new double[n + 6], yData2 = new double[n + 6];
                    for (int i = 0; i < (n + 6); i++) {
                        xData[i] = a + (i - 3) * (b - a) / n;
                        yData1[i] = function.calculate(xData[i]);
                        yData2[i] = iFunc.calculate(xData[i]);
                    }
                    createGraph(iFunc).show();
                }
            });

            bCount.addActionListener(ev -> {
                initParams();
                LagrangePolynomial polynomial = new LagrangePolynomial(function,a,b,n);
                fieldResult.setText(polynomial.count((double)sX.getValue())+"");
            });

            c.insets = new Insets(5, 10, 5, 10);
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 1;
            c.weightx = 1;
            c.weighty = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.NORTHWEST;
            gb.setConstraints(func, c);
            add(func);

            c.gridy = 1;
            gb.setConstraints(funcSin, c);
            add(funcSin);
            c.gridy = 2;
            gb.setConstraints(funcSqrt, c);
            add(funcSqrt);
            c.gridy = 3;
            gb.setConstraints(funcSqr, c);
            add(funcSqr);
            c.gridy = 4;
            gb.setConstraints(funcExp, c);
            add(funcExp);
            c.gridy = 5;

            c.gridx = 2;
            c.gridy = 0;
            gb.setConstraints(lDots, c);
            add(lDots);
            c.gridheight = 1;
            c.gridy = 1;
            gb.setConstraints(sDotsNum, c);
            add(sDotsNum);
            c.gridheight = 1;
            c.gridy = 2;
            gb.setConstraints(lims, c);
            add(lims);
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.2;
            gb.setConstraints(lFrom, c);
            add(lFrom);
            c.gridx = 3;
            c.weightx = 1;
            gb.setConstraints(sMin, c);
            add(sMin);
            c.gridx = 2;
            c.gridy = 4;
            c.weightx = 0.2;
            gb.setConstraints(lTo, c);
            add(lTo);
            c.gridx = 3;
            c.weightx = 1;
            gb.setConstraints(sMax, c);
            add(sMax);

            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.NORTH;
            c.gridx = 0;
            c.gridwidth = 4;
            c.gridy = 5;
            gb.setConstraints(bCheck, c);
            add(bCheck);
            c.gridy = 6;
            c.gridwidth = 2;
            gb.setConstraints(lX, c);
            add(lX);
            c.gridy = 7;
            c.fill = GridBagConstraints.HORIZONTAL;
            gb.setConstraints(sX, c);
            add(sX);
            c.fill = GridBagConstraints.NONE;
            c.gridy = 6;
            c.gridx = 2;
            gb.setConstraints(lY, c);
            add(lY);
            c.gridy = 7;
            gb.setConstraints(fieldResult, c);
            add(fieldResult);
            c.gridwidth = 4;
            c.gridy = 8;
            c.gridx = 0;
            gb.setConstraints(bCount, c);
            add(bCount);

            this.setVisible(true);
        }

        private void initParams(){
            a = (int)sMin.getValue();
            b = (int)sMax.getValue();
            n = (int)sDotsNum.getValue();
            if (funcSin.isSelected()) f=0;
            if (funcSqrt.isSelected()) f=1;
            if (funcSqr.isSelected()) f=2;
            if (funcExp.isSelected()) f=3;
            functionChooser(f);
            if (a>b){
                a += b;
                b = a-b;
                a = a-b;
            }
        }

        public void functionChooser(int index){
            switch (index){
                case 0: function = Math::sin;break;
                case 1: function = Math::sqrt;break;
                case 2: function = (double x) -> Math.pow(x,2);break;
                case 3: function = Math::exp;break;
                case 4: function = Math::log;break;
            }
        }

    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(InputGUI::new);
    }

    public  static JFrame createGraph(MathFunction iFunc) {
        XYSeries series1,series2,series3;

        series1 = new XYSeries("f(x)");
        series2 = new XYSeries("p(x)");
        series3 = new XYSeries("InterpolatePoints");
        for (int i = 0; i < (b-a+2)*10; i ++) {
            series1.add((a*10 + i - 10) / 10, function.calculate((a*10 + i - 10) / 10));
            series2.add((a*10 + i - 10) / 10, iFunc.calculate((a*10 + i - 10) / 10));
        }

        for (int i = a; i < b; i ++) {
            series3.add((i+((b-a)/n)), function.calculate((i+((b-a)/n))));
        }

        XYSeriesCollection dataset1 = new XYSeriesCollection();
        XYSeriesCollection dataset2 = new XYSeriesCollection();
        XYSeriesCollection dataset3 = new XYSeriesCollection();
        dataset1.addSeries(series1);
        dataset2.addSeries(series2);
        dataset3.addSeries(series3);

        XYPlot plot = new XYPlot();

        //plot.setDataset(2, dataset3);
        plot.setDataset(0, dataset1);
        plot.setDataset(1, dataset2);

        XYSplineRenderer splinerenderer0 = new XYSplineRenderer();

        plot.setRenderer(0, splinerenderer0);//use default fill paint for first series
        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        splinerenderer.setSeriesFillPaint(0, Color.RED);
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
        splinerenderer1.setSeriesFillPaint(1, Color.BLUE);
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
        splinerenderer2.setSeriesFillPaint(2, Color.GREEN);
        splinerenderer2.setSeriesLinesVisible(0,false);
        plot.setRenderer(0, splinerenderer);
        plot.setRenderer(1, splinerenderer1);
        //plot.setRenderer(2, splinerenderer2);
        NumberAxis numberAxis = new NumberAxis();
        plot.setRangeAxis(0, numberAxis);
        //plot.setRangeAxis(1,numberAxis,false);
        //plot.setRangeAxis(2,numberAxis,false);
        plot.setDomainAxis(new NumberAxis("X"));

        plot.mapDatasetToRangeAxis(1, 0);

        JFreeChart chart = new JFreeChart("Метод Лагранжа", new Font("Courier New", Font.BOLD, 20), plot, true);
        chart.setBackgroundPaint(Color.WHITE);
        JPanel chartPanel = new ChartPanel(chart);
        JFrame chartFrame = new JFrame();
        chartFrame.setLayout(null);
        chartFrame.add(chartPanel);
        chartPanel.setBounds(5,5,500,500);
        chartFrame.setSize(new Dimension(550,560));
        chartFrame.setTitle("График");
        return chartFrame;
    }
}
