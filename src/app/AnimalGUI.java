package app;

import java.awt.*;

public class AnimalGUI extends Frame {
    // networking part
    Panel netWorkingPanel = new Panel();
    Checkbox cbNetWork = new Checkbox("Сеть", false);

    // listing part
    Panel listingPanel = new Panel();
    CheckboxGroup cbgListingGroup = new CheckboxGroup();
    Label allFoodsLabel, predatorsLabel, herbivoresLabel, grassLabel;
    Scrollbar scrollbar = new Scrollbar();
    Checkbox cb1, cb2, cb3, cb4;

    List list = new List();





    public AnimalGUI() throws HeadlessException {
        setSize(800, 700);
        setLayout(new FlowLayout());

        initialiseNetworkingPart();

        initialiseListingPart();

        setVisible(true);
    }

    private void initialiseNetworkingPart() {
        netWorkingPanel.setSize(700, 600);

        netWorkingPanel.setBounds(40, 80, 200, 400);
        netWorkingPanel.setBackground(Color.LIGHT_GRAY);

        scrollbar.setBounds(100, 100, 50, 140);
        netWorkingPanel.add(cbNetWork);
        netWorkingPanel.setLayout(new FlowLayout());
        netWorkingPanel.setLocation(350, 550); // useless with flowlayout

        add(netWorkingPanel);
    }

    private void initialiseListingPart() {
        cb1 = new Checkbox("Просмотреть всех", cbgListingGroup, false);
        cb2 = new Checkbox("Просмотреть хищников", cbgListingGroup, false);
        cb3 = new Checkbox("Просмотреть травоядных", cbgListingGroup, false);
        cb4 = new Checkbox("Просмотреть травы", cbgListingGroup, false);

        allFoodsLabel = new Label();
        predatorsLabel = new Label();
        herbivoresLabel = new Label();
        grassLabel = new Label();

        listingPanel.add(cb1);
        listingPanel.add(allFoodsLabel);
        listingPanel.add(cb2);
        listingPanel.add(predatorsLabel);
        listingPanel.add(cb3);
        listingPanel.add(herbivoresLabel);
        listingPanel.add(cb4);
        listingPanel.add(grassLabel);

        listingPanel.setBounds(140, 180, 200, 400);
        listingPanel.setBackground(Color.LIGHT_GRAY);

        scrollbar.setBounds(100, 100, 50, 140);

        listingPanel.add(list);
        listingPanel.add(scrollbar);

        listingPanel.setLayout(new FlowLayout());
        listingPanel.setLocation(100, 100);

        add(listingPanel);


        for (int i = 0; i < 30; i++)
            list.add(Integer.toString(i));
    }
}
