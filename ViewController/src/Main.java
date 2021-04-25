import java.sql.CallableStatement;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import mnj.model.service.AppModuleImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

import oracle.jdbc.OracleTypes;

public class Main {
    private RichInputText buyerID;
    private RichInputText org_ID;
    private RichTable orderTable;
    private RichPanelFormLayout panelBinding;
    private RichPanelCollection panelCallectionTab;
    private RichTable orderVO;
    private RichPanelCollection panelbind;
    private RichTable orderSummaryReport;
    private RichTable order_recap_new;

    public Main() {
    }
    
    public AppModuleImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("AppModuleDataControl"); // Name of application module in datacontrolBinding.cpx
        AppModuleImpl appM = (AppModuleImpl)dc.getDataProvider();
        return appM;
    }
    AppModuleImpl am =  getAppModuleImpl();

    public String action_call() {
       // AdfFacesContext.getCurrentInstance().addPartialTarget(orderTable);
       //  AdfFacesContext.getCurrentInstance().addPartialTarget(panelBinding);
        ViewObject searchVO=am.getsearchVO1();
        ViewObject OrgVO=am.getselectORG1();
        ViewObject oder=am.getorder_recap_new_view1();
        oder.clearCache();
        refresh();
        String season=null;
        int Buyer=0;
        int org=0;
        String orgname=null;
        try{
            season=searchVO.getCurrentRow().getAttribute("Season").toString();
        }
        catch(Exception e) {
            season=null;
        }
        try{
            Buyer=Integer.parseInt(searchVO.getCurrentRow().getAttribute("BuyerId").toString());
        }
        catch(Exception e) {
            Buyer=0;
        }
        try{
            org=Integer.parseInt(OrgVO.getCurrentRow().getAttribute("OrgId").toString());
        }
        catch(Exception e) {
           org=0;
        }
        try{
            orgname=OrgVO.getCurrentRow().getAttribute("Org").toString();
        }
        catch(Exception e) {
           orgname=null;
        }
        System.out.println("org.............."+org);
        System.out.println("Buyer............."+Buyer);
        
        System.out.println("Season.............."+season);
            String value ="failed";
        
       int pram=1;
       am.getDBTransaction().commit();
      /***ViewObject oder=am.getOrderRecapSummary1();
      ViewObject oder=am.getorder_recap_new_view1();
        oder.setNamedWhereClauseParam("param",pram);
        oder.setWhereClause("SEASON = '"+season+"' AND BUYER_ID = '"+Buyer+"' AND ORG_ID= '"+org+"'");
       **/
       
     
        oder.setNamedWhereClauseParam("param",pram);
        oder.setWhereClause("SEASON = '"+season+"' AND BUYER_ID = '"+Buyer+"' AND LC_UNIT= '"+orgname+"'");
       
       
        //oder.setWhereClause("BUYER_ID = '"+Buyer+"'");
        //oder.setWhereClause("ORG_ID= '"+org+"'");
        oder.executeQuery();
        oder.clearCache();
       
       //AdfFacesContext.getCurrentInstance().addPartialTarget(orderSummaryReport);
      AdfFacesContext.getCurrentInstance().addPartialTarget(order_recap_new); 
       AdfFacesContext.getCurrentInstance().addPartialTarget(panelbind);
        return null;
    }

    public void setByerId(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
     ViewObject buyerVO=   am.getsearchVO1();
//     String buyerId=   buyerVO.getCurrentRow().getAttribute("BuyerId").toString();
  //   System.out.println("........................buyer id ="+buyerId);
        AdfFacesContext.getCurrentInstance().addPartialTarget(buyerID);
        
    }

    public void setBuyerID(RichInputText buyerID) {
        this.buyerID = buyerID;
    }

    public RichInputText getBuyerID() {
        return buyerID;
    }

    public void setOrgID(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(org_ID);
        
    }

    public void setOrg_ID(RichInputText org_ID) {
        this.org_ID = org_ID;
    }

    public RichInputText getOrg_ID() {
        return org_ID;
    }

    public void setOrderTable(RichTable orderTable) {
        this.orderTable = orderTable;
    }

    public RichTable getOrderTable() {
        return orderTable;
    }

    public void setPanelBinding(RichPanelFormLayout panelBinding) {
        this.panelBinding = panelBinding;
    }

    public RichPanelFormLayout getPanelBinding() {
        return panelBinding;
    }

    public void setPanelCallectionTab(RichPanelCollection panelCallectionTab) {
        this.panelCallectionTab = panelCallectionTab;
    }

    public RichPanelCollection getPanelCallectionTab() {
        return panelCallectionTab;
    }

    public void setOrderVO(RichTable orderVO) {
        this.orderVO = orderVO;
    }

    public RichTable getOrderVO() {
        return orderVO;
    }

    public void setPanelbind(RichPanelCollection panelbind) {
        this.panelbind = panelbind;
    }

    public RichPanelCollection getPanelbind() {
        return panelbind;
    }

    public void setOrderSummaryReport(RichTable orderSummaryReport) {
        this.orderSummaryReport = orderSummaryReport;
    }

    public RichTable getOrderSummaryReport() {
        return orderSummaryReport;
    }

    public void setOrder_recap_new(RichTable order_recap_new) {
        this.order_recap_new = order_recap_new;
    }

    public RichTable getOrder_recap_new() {
        return order_recap_new;
    }
    
    public void refresh() {
                 FacesContext facesContext = FacesContext.getCurrentInstance();
                 String refreshpage = facesContext.getViewRoot().getViewId();
                 ViewHandler  viewHandler =facesContext.getApplication().getViewHandler();
                 UIViewRoot viewroot =  viewHandler.createView( facesContext, refreshpage);
                 viewroot.setViewId(refreshpage);
                 facesContext.setViewRoot(viewroot);
           }
    
    
    
}
