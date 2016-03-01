package client.roll;

import client.base.*;
import java.util.Random;

import client.control.IObserver;
import client.control.Reference;
import shared.definitions.TurnStatus;
import shared.model.Game;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, IObserver {
	private Reference r;
	private Game model;

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		r = Reference.GET_SINGLETON();
		model = Reference.GET_SINGLETON().getFascade().getModel();
		setResultView(resultView);
		Reference.GET_SINGLETON().getFascade().addObserver(this);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	/**
	 * @pre it is the players turn
	 * @post simulates a roll of the dice and displays the result and distributes the required cards.
	 */
	@Override
	public void rollDice() 
	{
		Random rand=new Random();
		final int result=rand.nextInt(6)+rand.nextInt(6)+2;
		getResultView().setRollValue(result);
		getResultView().showModal();
		Reference.GET_SINGLETON().getProxy().rollNumber(result);
		Reference.GET_SINGLETON().getProxy().getModel(Reference.GET_SINGLETON().getFascade().getLatestModelNum());
	}

	@Override
	public void ObservableChanged() 
	{
		model = r.getFascade().getModel();
		if(model == null) return;
		if(model.getTurn_tracker().getActive_player() == r.getPlayer_index()
				&& model.getTurnStatus(r.getPlayer_index()) == TurnStatus.ROLLING)
		{
			 if(!getRollView().isModalShowing()) {
					 getRollView().showModal();
			 }
		}
	}

}

