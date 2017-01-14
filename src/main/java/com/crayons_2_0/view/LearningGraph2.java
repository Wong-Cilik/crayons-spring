package com.crayons_2_0.view;

import com.crayons_2_0.service.SwingFrame;
import com.hs18.vaadin.addon.graph.GraphJSComponent;
import com.hs18.vaadin.addon.graph.GraphJsNode;
import com.hs18.vaadin.addon.graph.listener.GraphJsLeftClickListener;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;



import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;


/**
 * LINKS:
 * 
 * https://github.com/singhalavi/graph-tree/tree/master/src/main/java/com/hs18/vaadin/addon/graph
 * 
 *  https://vaadin.com/directory#!addon/graph-explorer
 *  
 *  
 *  https://github.com/tesis-dynaware/graph-editor
 *  
 *  https://www.informatik-aktuell.de/entwicklung/programmiersprachen/swing-und-javafx-migration-und-rundumerneuerung.html
 *  
 *  
 *  https://vaadin.com/directory#!addon/nodegraphwidget
 */



public class LearningGraph2 extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
    public static final String VIEW_NAME = "LearningGraph2";
    
    GraphJSComponent graphJSComponent;
    
    public LearningGraph2() {
    	
    	final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        addComponent(layout);
        
        /*
    	graphJSComponent = new GraphJSComponent();
    	graphJSComponent.setNodesSize(120, 50);
		graphJSComponent.setLeftClickListener(new GraphJsLeftClickListener() {
			
			@Override
			public void onLeftClick(String id, String type, String parentId) {
				System.out.println(id + " "+ type + " "+ parentId);
				Notification.show("Clicked on node with id = " + id + " at " + type, Notification.Type.WARNING_MESSAGE); 
			}
		});
        graphJSComponent.setImmediate(true);
        
        String lhtml = "<div id='graph' class='graph' ></div>";//add style='overflow:scroll' if required
        Label graphLabel = new Label(lhtml, Label.CONTENT_XHTML);
        
        layout.addComponent(graphLabel);
        layout.addComponent(graphJSComponent);
        prepareGraph();
        */
        
        neuesFenster();
        
        
        //---------------------------------------------------------------------------------
        // Graph-Editor
        
        
        
        NodeGraphWidget();
        
        
        
        //----------------------------------------------------------------------------------
       
    }
    
    private void NodeGraphWidget() {
    	
    	/*
    	// = Create the model.
    	final GraphModel graphModel = GraphModelFactory.getGraphModelInstance();

    	// = Support for single click to select nodes.
    	graphModel.setSingleSelectionSupport(Boolean.TRUE);

    	// = Create some nodes.
    	Node begin = new BeginNode();

    	Node splitNode = new SplitNode();

    	Node nodeA = new DefaultNode();
    	nodeA.setLabel("NodeA");

    	Node nodeB = new DefaultNode();
    	nodeB.setLabel("NodeB");

    	Node processCreation = new ProcessCreationNode();
    	processCreation.setLabel("Process");

    	Node end = new EndNode();

    	// = Nodes meet nodes!.
    	final RelationStyle dashedBlue = new DefaultRelationStyle(GraphConstants.DOM.CSS_BLUE_VALUE, 2);
    	dashedBlue.setDashedStroke(5, 5);

    	final RelationStyle straightRed = new DefaultRelationStyle();
    	straightRed.strokeColor(GraphConstants.DOM.CSS_RED_VALUE).strokeWidth(3);

    	final RelationStyle defaultNormalBlack = new DefaultRelationStyle();

    	// = Tie all the stuff.
    	graphModel.connect(begin, splitNode, straightRed);
    	graphModel.connect(splitNode, nodeA, dashedBlue);
    	graphModel.connect(splitNode, nodeB, defaultNormalBlack);
    	graphModel.connect(nodeA, end, RelationTypeEnum.BEZIER);
    	graphModel.connect(nodeB, processCreation, RelationTypeEnum.BEZIER, new DefaultRelationStyle().strokeColor(GraphConstants.DOM.CSS_GREEN_VALUE));
    	graphModel.connect(processCreation, end, RelationTypeEnum.LINE);

    	// = Retrive the model from somewhere...
    	final GraphModel graphModel = getGraphModel ... ;

    	// = Create the component and pass the model on its constructor.
    	final GraphComponent component = new GraphComponent(graphModel);
    	component.setSizeFull();

    	// = Add the component to a container...
    	final VerticalLayout layout = new VerticalLayout();
    	layout.setMargin(true);
    	layout.setSpacing(true);
    	layout.addComponent(component);
    	*/ 
		
	}

	private void neuesFenster() {
    	// Some UI logic to open the sub-window
    	final Button open = new Button("Open Sub-Window");
    	open.addClickListener(new ClickListener() {
    	    public void buttonClick(ClickEvent event) {
    	        MySub sub = new MySub();

    	        // Add it to the root component
    	        UI.getCurrent().addWindow(sub);
    	    }
    	});
    	addComponent(open);
	}
    
   
    
    
    // Define a sub-window by inheritance
    class MySub extends Window {
        public MySub() {
            super("Subs on Sale"); // Set window caption
            center();

            // Some basic content for the window
            VerticalLayout content = new VerticalLayout();
            
            content.addComponent(new Label("Neues Fenster"));
            content.setMargin(true);
            setContent(content);

            // Disable the close button
            setClosable(false);
            
            
            // LEVIN
            
            graphJSComponent = new GraphJSComponent();
        	graphJSComponent.setNodesSize(120, 50);
    		graphJSComponent.setLeftClickListener(new GraphJsLeftClickListener() {
    			
    			@Override
    			public void onLeftClick(String id, String type, String parentId) {
    				System.out.println(id + " "+ type + " "+ parentId);
    				Notification.show("Clicked on node with id = " + id + " at " + type, Notification.Type.WARNING_MESSAGE); 
    			}
    		});
            graphJSComponent.setImmediate(true);
            
            String lhtml = "<div id='graph' class='graph' ></div>";//add style='overflow:scroll' if required
            Label graphLabel = new Label(lhtml, Label.CONTENT_XHTML);
            
            content.addComponent(graphLabel);
            content.addComponent(graphJSComponent);
            prepareGraph();
            
            

            // Trivial logic for closing the sub-window
            Button ok = new Button("OK");
            ok.addClickListener(new ClickListener() {
                public void buttonClick(ClickEvent event) {
                    close(); // Close the sub-window
                }
            });
            content.addComponent(ok);
            
            SwingFrame swingFrame = new SwingFrame();
            
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
    private void prepareGraph() {
    	try {
			graphJSComponent.addNode("fruits", "Fruits I Like", "level 1", null, null);//Give parent id as null for root node
			graphJSComponent.getNodeProperties("fruits").put("title", "Fruits I Like"); 
			graphJSComponent.addNode("watermelon", "Watermelon", "level 2", null, "fruits");//first child of node with id fruits
			graphJSComponent.getNodeProperties("watermelon").put("title", "Its a very juicy fruit."); 
			graphJSComponent.addNode("mango", "Mango", "level 2", null, "fruits");//second child of node with id fruits
			graphJSComponent.getNodeProperties("mango").put("title", "Katrina Kaif's favourite."); 
			graphJSComponent.addNode("apple", "Apple", "level 2", null, "fruits");//third child of node with id fruits
			graphJSComponent.getNodeProperties("apple").put("title", "One apple a day, keeps the doctor away"); 
			graphJSComponent.getNodeProperties("apple").put("fill", "#F00");
			graphJSComponent.getNodeProperties("mango").put("fill", "yellow");
			
			graphJSComponent.addNode("5", "Hapoos", "level 3", null, "mango");//child of mango node
			graphJSComponent.getNodeProperties("5").put("title", "One of the best mangos"); 
			
			graphJSComponent.addNode("6", "Green", "level 3", null, "watermelon");//child of watermelon node
			graphJSComponent.getNodeProperties("6").put("title", "Green from outside, red inside"); 
			graphJSComponent.getNodeProperties("6").put("fill", "#0F0");
			
			//Another Tree in the same graph
			graphJSComponent.addNode("fruitsnotlike", "Fruits I Dont Like", "level 1",  null, null);//Give parent id as null
			graphJSComponent.getNodeProperties("fruitsnotlike").put("title", "Another tree in the same graph"); 
			graphJSComponent.addNode("lichy", "Lichy", "level 2", null, "fruitsnotlike");//first child of node with id fruitsnotlike
			graphJSComponent.getNodeProperties("lichy").put("title", "because its nto easy to eat it."); 
			graphJSComponent.addNode("redlichy", "Red Lichy", "level 3", null, "lichy");
			graphJSComponent.getNodeProperties("redlichy").put("title",  "red lichy"); 
			
			graphJSComponent.refresh();//Call refresh after you are done with your changes
		} catch (Exception e) {
			Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE); 
			e.printStackTrace();
		}//
		
	}

	private void magic() {
    	/*
    	//Prepare
    	GraphJSComponent graphJSComponent = new GraphJSComponent();
    	graphJSComponent.setNodesSize(120, 50);
    	graphJSComponent.setLeftClickListener(new GraphJsLeftClickListener() { 
			@Override
            public void onLeftClick(String id, String type, String parentId) {
                System.out.println(id + " "+ type + " "+ parentId);
            }
        });
    	graphJSComponent.setImmediate(true);
    	addComponent(graphJSComponent);
    	
    	//Root Node
    	
    	try {
    		
			graphJSComponent.addNode("rootNode", "RootNode", "level 1", null, null);
			graphJSComponent.getNodeProperties("rootNode").put("title", "click left");
			
			graphJSComponent.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
    	
    	GraphJSComponent graphJSComponent = new GraphJSComponent();
        graphJSComponent.setNodesSize(120, 50);
        graphJSComponent.setLeftClickListener(new GraphJsLeftClickListener() {
            
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void onLeftClick(String id, String type, String parentId) {
                //System.out.println(id + " "+ type + " "+ parentId);
				Notification.show("Clicked on node with id = " + id + " at " + type, Notification.Type.WARNING_MESSAGE); 
            }
        });
        graphJSComponent.setImmediate(true);
        
        String lhtml = "<div id='graph' class='graph' style='overflow:scroll' ></div>";//add style='overflow:scroll' if required
        Label graphLabel = new Label(lhtml, Label.CONTENT_XHTML);
        
        addComponent(graphLabel);
        addComponent(graphJSComponent);
        
        
    	try {
            
    		graphJSComponent.addNode("fruits", "Fruits I Like", "level 1", null, null);//Give parent id as null for root node
            graphJSComponent.getNodeProperties("fruits").put("title", "Fruits I Like"); 
             
            graphJSComponent.refresh();//Call refresh after you are done with your changes
        } catch (Exception e) {
            e.printStackTrace();
        }//
    	
    	
    }
    



	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
