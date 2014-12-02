package com.nehp.rfid_system.reader;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

import com.intermec.datacollection.rfid.*;

public class ReaderApp extends JFrame {

	private static final String TAG_TYPE_EPCC1G2 = "EPC Gen2";
	private static final String TAG_TYPE_ISOMIXED = "ISO Mixed";

	private static final String READ_OPT_SINGLESHOT = "Singleshot read";
	private static final String READ_OPT_POLL = "Cont. read Poll";
	private static final String READ_OPT_EVENT = "Cont. read Event";

	private static final int    READ_OPT_INDEX_SINGLESHOT = 0;
	private static final int    READ_OPT_INDEX_POLL = 1;
	private static final int    READ_OPT_INDEX_EVENT = 2;

	private BRIReader          m_Reader = null;
	private RFIDButtonAdapter  m_RFIDCenterBtnAdapter = null;
	private RFIDButtonAdapter  m_RFIDLeftBtnAdapter = null;
	private RFIDButtonAdapter  m_RFIDRightBtnAdapter = null;
	private RFIDButtonAdapter  m_RFIDMiddleBtnAdapter = null;
	private String             m_sFieldSchema = "ANT";
	private boolean            m_fContinuousReadInProgress = false;
	
	//*****
	//* UI Components
	//*****
	private Panel          m_PanelNorth = new Panel(new GridBagLayout());
	private Label          m_LabelTagType = new Label("Tag Type:");
	private Choice         m_ChoiceTagTypes = new Choice();
	private Label          m_LabelReadOptions = new Label("Read Mode:");
	private Choice         m_ChoiceReadOptions = new Choice();
	private Panel          m_PanelReadButtons = new Panel(new FlowLayout(FlowLayout.CENTER, 30, 5));
	private Button         m_BtnStartRead = new Button("Start Read");
	private Button         m_BtnStopRead = new Button("Stop Read");
	private Panel          m_PanelRFIDButtons = new Panel(new FlowLayout(FlowLayout.LEFT, 3, 2));
	private Checkbox       m_CBCenterTrigger = new Checkbox("C. Trigger");
	private Checkbox       m_CBLetfBtn = new Checkbox("L.");
	private Checkbox       m_CBMiddleBtn = new Checkbox("M.");
	private Checkbox       m_CBRightBtn = new Checkbox("R. Button");
	private TextArea       m_TextArea = new TextArea();
	private Panel          m_PanelSouth = new Panel(new FlowLayout(FlowLayout.CENTER, 50, 5));
	private Button         m_BtnClear = new Button("Clear");
	private Button         m_BtnExit = new Button("Exit");
	
	private static final long serialVersionUID = 6950818609030140261L;

	public ReaderApp(String title)
	{
		super(title);
		initComponents();
		try
		{
			m_Reader = new BRIReader();
		}
		catch (BasicReaderException bre)
		{
			m_TextArea.append("Exception occurs during reader connection:\n");
			m_TextArea.append(bre.getMessage() + "\n");
			return;
		}
		init();
	}
	
	/**
	 * Layouts the UI components.
	 */
	private void initComponents()
	{
		//*****
		//* Sets the initial state of the control.
		//*****
		m_LabelTagType.setEnabled(false);
		m_ChoiceTagTypes.setEnabled(false);
		m_LabelReadOptions.setEnabled(false);
		m_ChoiceReadOptions.setEnabled(false);
		m_BtnStartRead.setEnabled(false);
		m_BtnStopRead.setEnabled(false);
		m_CBCenterTrigger.setEnabled(false);
		m_CBLetfBtn.setEnabled(false);
		m_CBMiddleBtn.setEnabled(false);
		m_CBRightBtn.setEnabled(false);

		setLayout(new BorderLayout());
		add(m_PanelNorth, BorderLayout.NORTH);

		java.awt.GridBagConstraints constraintsLabelTagType = new java.awt.GridBagConstraints();
		constraintsLabelTagType.gridx = 0; constraintsLabelTagType.gridy = 0;
		constraintsLabelTagType.anchor = java.awt.GridBagConstraints.WEST;
		constraintsLabelTagType.insets = new java.awt.Insets(5, 5, 5, 0);
		m_PanelNorth.add(m_LabelTagType, constraintsLabelTagType);

		java.awt.GridBagConstraints constraintsChoiceTagType = new java.awt.GridBagConstraints();
		constraintsChoiceTagType.gridx = 1; constraintsChoiceTagType.gridy = 0;
		constraintsChoiceTagType.anchor = java.awt.GridBagConstraints.WEST;
		constraintsChoiceTagType.fill = java.awt.GridBagConstraints.HORIZONTAL;
		constraintsChoiceTagType.insets = new java.awt.Insets(5, 5, 5, 5);
		constraintsChoiceTagType.weightx = 1.0;
		m_PanelNorth.add(m_ChoiceTagTypes, constraintsChoiceTagType);
		
		m_ChoiceTagTypes.add(TAG_TYPE_EPCC1G2);
		m_ChoiceTagTypes.add(TAG_TYPE_ISOMIXED);
		
		/**
		 * Adds a listener to listen for the item selection event
		 * in the "Tag Type" choice menu.
		 **/
		m_ChoiceTagTypes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					setTagType(e.getItem().toString());
				}
			}
		});
		
		java.awt.GridBagConstraints constraintsLabelReadOpt = new java.awt.GridBagConstraints();
		constraintsLabelReadOpt.gridx = 0; constraintsLabelReadOpt.gridy = 1;
		constraintsLabelReadOpt.anchor = java.awt.GridBagConstraints.WEST;
		constraintsLabelReadOpt.insets = new java.awt.Insets(0, 5, 3, 0);
		m_PanelNorth.add(m_LabelReadOptions, constraintsLabelReadOpt);
		
		java.awt.GridBagConstraints constraintsChoiceReadOpt = new java.awt.GridBagConstraints();
		constraintsChoiceReadOpt.gridx = 1; constraintsChoiceReadOpt.gridy = 1;
		constraintsChoiceReadOpt.anchor = java.awt.GridBagConstraints.WEST;
		constraintsChoiceReadOpt.fill = java.awt.GridBagConstraints.HORIZONTAL;
		constraintsChoiceReadOpt.insets = new java.awt.Insets(0, 5, 3, 5);
		m_PanelNorth.add(m_ChoiceReadOptions, constraintsChoiceReadOpt);
		
		m_ChoiceReadOptions.add(READ_OPT_SINGLESHOT);
		m_ChoiceReadOptions.add(READ_OPT_POLL);
		m_ChoiceReadOptions.add(READ_OPT_EVENT);
		
		java.awt.GridBagConstraints constraintsPanelRead = new java.awt.GridBagConstraints();
		constraintsPanelRead.gridx = 0; constraintsPanelRead.gridy = 2;
		constraintsPanelRead.anchor = java.awt.GridBagConstraints.WEST;
		constraintsPanelRead.gridwidth = 0;
		constraintsPanelRead.insets = new java.awt.Insets(0, 0, 3, 0);
		m_PanelNorth.add(m_PanelReadButtons, constraintsPanelRead);

		m_PanelReadButtons.add(m_BtnStartRead);
		// Register action listener for the "Start Read" button
		m_BtnStartRead.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				readTags();
			}
		});
		
		m_PanelReadButtons.add(m_BtnStopRead);
		// Register action listener for the "Stop Read" button
		m_BtnStopRead.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				stopContinuousRead();
			}
		});
		
		java.awt.GridBagConstraints constraintsPanelRFIDBtns = new java.awt.GridBagConstraints();
		constraintsPanelRFIDBtns.gridx = 0; constraintsPanelRFIDBtns.gridy = 3;
		constraintsPanelRFIDBtns.anchor = java.awt.GridBagConstraints.WEST;
		constraintsPanelRFIDBtns.gridwidth = 0;
		constraintsPanelRFIDBtns.insets = new java.awt.Insets(0, 0, 0, 0);
		m_PanelNorth.add(m_PanelRFIDButtons, constraintsPanelRFIDBtns);

		m_PanelRFIDButtons.add(m_CBCenterTrigger);
		//*****
		//* Adds a listener to listen for the state of the m_CBCenterTrigger
		//* check box.
		//*****
		m_CBCenterTrigger.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					setCenterTriggerNotify(true);
				}
				else
				{
					setCenterTriggerNotify(false);
				}
			}
		});
		
		m_PanelRFIDButtons.add(m_CBLetfBtn);
		//*****
		//* Adds a listener to listen for the state of the m_CBLetfBtn
		//* check box.
		//*****
		m_CBLetfBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					setLeftButtonNotify(true);
				}
				else
				{
					setLeftButtonNotify(false);
				}
			}
		});

		m_PanelRFIDButtons.add(m_CBMiddleBtn);
		//*****
		//* Adds a listener to listen for the state of the m_CBLetfBtn
		//* check box.
		//*****
		m_CBMiddleBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					setMiddleButtonNotify(true);
				}
				else
				{
					setMiddleButtonNotify(false);
				}
			}
		});

		m_PanelRFIDButtons.add(m_CBRightBtn);
		//*****
		//* Adds a listener to listen for the state of the m_CBRightBtn
		//* check box.
		//*****
		m_CBRightBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					setRightButtonNotify(true);
				}
				else
				{
					setRightButtonNotify(false);
				}
			}
		});

		// Use monospaced font in the text box.
		//m_TextArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
		add(m_TextArea, BorderLayout.CENTER);

		add(m_PanelSouth, BorderLayout.SOUTH);

		m_PanelSouth.add(m_BtnClear);
		// Register action listener for the Clear button
		m_BtnClear.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				m_TextArea.setText("");
			}
		});

		m_PanelSouth.add(m_BtnExit);
		// Register action listener for the Exit button
		m_BtnExit.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				exitApp();
			}
		});
	}
	

	/**
	 * Initializes the application after a successful reader connection.
	 * It will set the initial state of the buttons and check boxes.
	 */
	private void init()
	{
		boolean     fEnabled;

		// This method is only called after a successful connection so
		// we could enable the reader related controls now.
		m_LabelTagType.setEnabled(true);
		m_ChoiceTagTypes.setEnabled(true);
		m_LabelReadOptions.setEnabled(true);
		m_ChoiceReadOptions.setEnabled(true);
		m_BtnStartRead.setEnabled(true);
		
		//*****
		//* Set up the listener for the battery events.
		//*****
		m_Reader.addBatteryEventListener(new BatteryEventListener()
		{
			public void receivedBatteryEvent(BatteryEvent aEvent)
			{
				final BatteryEvent batteryEvent = aEvent;
				/**
				 * Uses EventQueue.invokeLater to ensure the UI update
				 * executes on the AWT event dispatching thread. 
				 */
				EventQueue.invokeLater(new Runnable() {
					public void run()
					{
						// Display the current battery state.
						switch (batteryEvent.getBatteryState())
						{
							case BatteryEvent.BatteryStates.CHARGED:
								m_TextArea.append("I-Battery state is CHARGED.\n");
								break;
							case BatteryEvent.BatteryStates.LOW:
								m_TextArea.append("I-Battery state is LOW.\n");
								break;
							case BatteryEvent.BatteryStates.OPERATIONAL:
								m_TextArea.append("I-Battery state is OPERATIONAL.\n");
								break;
							case BatteryEvent.BatteryStates.UNKNOWN:
								m_TextArea.append("I-Unknown battery state: " + batteryEvent.getBatteryStateString() + "\n");
								break;
						}
					}
				});
			} //endof receivedBatteryEvent
		});

		try
		{
			//*****
			//* Query the reader to see if the center trigger is enabled
			//* for generating RFID button event.
			//*****
			fEnabled = m_Reader.isRFIDButtonEnabled(BRIReader.RFIDButtonIDs.CENTER);
			// If exception occurs in the previous statement, the check box will
			// remain disabled so the user may not change its state. Note: The
			// query command is only supported in later version of DCE.
			m_CBCenterTrigger.setEnabled(true);
			m_CBCenterTrigger.setState(fEnabled);
			if (fEnabled)
			{
				// Adds a RFID button event listener
				m_Reader.addRFIDButtonListener(getCenterBtnAdapter());
			}
		}
		catch (Exception e)
		{
			m_TextArea.append("E-Failed to query center trigger button info.\n");
			m_TextArea.append(e.getMessage() + "\n");
		}

		try
		{
			//*****
			//* Query the reader to see if the left scan button is enabled
			//* for generating RFID button event.
			//*****
			fEnabled = m_Reader.isRFIDButtonEnabled(BRIReader.RFIDButtonIDs.LEFT);
			// If exception occurs in the previous statement, the check box will
			// remain disabled so the user may not change its state. Note: The
			// query command is only supported in later version of DCE.
			m_CBLetfBtn.setEnabled(true);
			m_CBLetfBtn.setState(fEnabled);
			if (fEnabled)
			{
				m_Reader.addRFIDButtonListener(getLeftBtnAdapter());
			}
		}
		catch (Exception e)
		{
			m_TextArea.append("E-Failed to query left scan button info.\n");
			m_TextArea.append(e.getMessage() + "\n");
		}
		
		try
		{
			//*****
			//* Query the reader to see if the middle scan button is enabled
			//* for generating RFID button event.
			//*****
			fEnabled = m_Reader.isRFIDButtonEnabled(BRIReader.RFIDButtonIDs.MIDDLE);
			// If exception occurs in the previous statement, the check box will
			// remain disabled so the user may not change its state. Note: The
			// query command is only supported in later version of DCE.
			m_CBMiddleBtn.setEnabled(true);
			m_CBMiddleBtn.setState(fEnabled);
			if (fEnabled)
			{
				m_Reader.addRFIDButtonListener(getMiddleBtnAdapter());
			}
		}
		catch (Exception e)
		{
			m_TextArea.append("E-Failed to query middle scan button info.\n");
			m_TextArea.append(e.getMessage() + "\n");
		}

		try
		{
			//*****
			//* Query the reader to see if the right scan button is enabled
			//* for generating RFID button event.
			//*****
			fEnabled = m_Reader.isRFIDButtonEnabled(BRIReader.RFIDButtonIDs.RIGHT);
			// If exception occurs in the previous statement, the check box will
			// remain disabled so the user may not change its state. Note: The
			// query command is only supported in later version of DCE.
			m_CBRightBtn.setEnabled(true);
			m_CBRightBtn.setState(fEnabled);
			if (fEnabled)
			{
				m_Reader.addRFIDButtonListener(getRightBtnAdapter());
			}
		}
		catch (Exception e)
		{
			m_TextArea.append("E-Failed to query right scan button info.\n");
			m_TextArea.append(e.getMessage() + "\n");
		}
		
		//*****
		//* Set the tag type to EPC Gen2 which is the default in
		//* the choice menu.
		//*****
		try
		{
			m_Reader.attributes.setTagTypes(ReaderAttributes.TagTypeMask.EPC_CLASS1_G2);
		}
		catch (Exception e)
		{
			m_TextArea.append("E-Failed to set tag type.\n");
			m_TextArea.append(e.getMessage() + "\n");
		}

		
		//*****
		//* Set up the tag event listener for the continuous read mode
		//*****
		m_Reader.addTagEventListener(new TagEventListener()
		{
			public void tagRead(TagEvent e)
			{
				final TagEvent tagEvent = e;
				/**
				 * Uses EventQueue.invokeLater to ensure the UI update
				 * executes on the AWT event dispatching thread. 
				 */
				EventQueue.invokeLater(new Runnable() {
					public void run()
					{
						displayTag(tagEvent.getTag());
					}
				});
			}
		});
		
		//*****
		//* Set up the listener for the DCE device connection state events.
		//*****
		m_Reader.addDCEDeviceConnectStateListener(new DCEDeviceConnectStateListener()
		{
			public void connectStateChanged(DCEDeviceConnectStateEvent e)
			{
				final DCEDeviceConnectStateEvent connEvent = e;
				/**
				 * Uses EventQueue.invokeLater to ensure the UI update
				 * executes on the AWT event dispatching thread. 
				 */
				EventQueue.invokeLater(new Runnable() {
					public void run()
					{
						// Uses the reader connection state to enable or
						// disable certain UI controls.
						updateUIControls(connEvent.getConnectState());
					}
				});
			} //endof connectStateChanged
		});

	} //end init()
	
	
	/**
	 * Enables or disables certain UI controls based on the reader
	 * connection state.
	 * @param aReaderConnectState an integer constant defined in
	 * DCEDeviceConnectStateEvent.ConnectStates to represent the reader
	 * connection state.
	 */
	public synchronized void updateUIControls(int aReaderConnectState)
	{
		boolean fOnLine = true;
		switch (aReaderConnectState)
		{
			case DCEDeviceConnectStateEvent.ConnectStates.OFFLINE:
				fOnLine = false;
				m_TextArea.append("I-The reader is off line.\n");
				break;
			case DCEDeviceConnectStateEvent.ConnectStates.CONNECTING:
				fOnLine = false;
				m_TextArea.append("I-DCE is reconnecting to the reader.\n");
				break;
			case DCEDeviceConnectStateEvent.ConnectStates.CONNECTED:
				m_TextArea.append("I-The reader is reconnected.\n");
				break;
			default:
				return;
		}

		//*****
		//* If the reader is not on line, the following UI controls will be
		//* disabled to prevent the user from interacting with the reader.
		//*****
		m_LabelTagType.setEnabled(fOnLine);
		m_ChoiceTagTypes.setEnabled(fOnLine);
		m_LabelReadOptions.setEnabled(fOnLine);
		m_ChoiceReadOptions.setEnabled(fOnLine);
		if (m_fContinuousReadInProgress)
		{
			// If a continuous read is in progress, the "Start Read"
			// button should remain disabled. Only ajdust the button
			// state of the "Stop Read" button.
			m_BtnStopRead.setEnabled(fOnLine);
		}
		else
		{
			// If a continuous read has not been started yet, the "Stop Read"
			// button should remain disabled. Only ajdust the button
			// state of the "Start Read" button.
			m_BtnStartRead.setEnabled(fOnLine);
		}
	}
	
	/**
	 * Sets the tag type according to specified string. This method
	 * is invoked from the item listener of the "Tag Type" choice
	 * menu.
	 * @param aTagType a selected item in the "Tag Type" choice menu
	 */
	public void setTagType(String aTagType)
	{
		if (m_Reader != null)
		{
			if (aTagType.equalsIgnoreCase(TAG_TYPE_ISOMIXED))
			{
				// Change tag type to ISO Mixed
				m_Reader.attributes.setTagTypes(ReaderAttributes.TagTypeMask.MIXED);
			}
			else
			{
				// Change tag type to EPC Gen2
				m_Reader.attributes.setTagTypes(ReaderAttributes.TagTypeMask.EPC_CLASS1_G2);
			}
		}
	}
	
	/**
	 * Issues a read according to the selected read option. 
	 * It will read all tags in the field and the tag data will
	 * contain the tag key and the antenna which is used for
	 * the read. This method is invoked from the action listener
	 * of the "Start Read" button. 
	 */
	public void readTags()
	{
		int iSelReadOpt = m_ChoiceReadOptions.getSelectedIndex();
		boolean  fSuccess;

		try
		{
			if (iSelReadOpt == READ_OPT_INDEX_EVENT)
			{ // Continuous read with event report
				m_TextArea.append("I-Continuous read with Event reporting.\n");

				// Read all tags with the field schema "ANT".
				fSuccess = m_Reader.startReadingTags(null, m_sFieldSchema,
						BRIReader.TagReportOptions.EVENT);
				if (fSuccess)
				{
					//*****
					//* Synchronize this block of code so that it cannot be
					//* executed at the same time as the updateUIControls method.
					//*****
					synchronized (this)
					{
						m_fContinuousReadInProgress = true;
						m_TextArea.append("I-Press \"Stop Read\" to stop continuous read.\n");
						// Do not allow to start another read until the
						// continuous read is stopped.
						m_BtnStartRead.setEnabled(false);
						m_BtnStopRead.setEnabled(true);
					}
				}
				else
				{
					m_TextArea.append("E-Failed to start continuous read.\n");
				}
			}
			else if (iSelReadOpt == READ_OPT_INDEX_POLL)
			{ // Continuous read with poll
				m_TextArea.append("I-Continuous read with Poll.\n");

				// Read all tags with the field schema "ANT".
				fSuccess = m_Reader.startReadingTags(null, m_sFieldSchema,
						BRIReader.TagReportOptions.POLL);
				if (fSuccess)
				{
					//*****
					//* Synchronize this block of code so that it cannot be
					//* executed at the same time as the updateUIControls method.
					//*****
					synchronized (this)
					{
						m_fContinuousReadInProgress = true;
						m_TextArea.append("I-Press \"Stop Read\" to Poll the tags and stop continuous read.\n");
						// Do not allow to start another read until the
						// continuous read is stopped.
						m_BtnStartRead.setEnabled(false);
						m_BtnStopRead.setEnabled(true);
					}
				}
				else
				{
					m_TextArea.append("E-Failed to start continuous read.\n");
				}
			}
			else
			{ // Singleshot read
				m_TextArea.append("I-Singleshot read.\n");

				// Read all tags with the field schema "ANT".
				if (true == m_Reader.read(null, m_sFieldSchema))
				{
					displayTags();
				}
				else
				{
					m_TextArea.append("NO tags read\n"); 
				}
			}
		} //endtry
		catch (Exception e)
		{
			m_TextArea.append(e.getMessage() + "\n");
		}
	}
	
	/**
	 * Stops the continuous read mode. If the read option is Poll,
	 * it will poll the tag list first before stopping the read.
	 * This method is invoked from the action listener of the
	 * "Stop Read" button.
	 */
	public void stopContinuousRead()
	{
		if (m_ChoiceReadOptions.getSelectedIndex() == READ_OPT_INDEX_POLL)
		{
			//*****
			//* If "Continuous read - Poll" option is selected,
			//* then poll the tag list first before stopping
			//* the continuous read mode.
			//*****
			try
			{
				if (true == m_Reader.pollTags())
				{
					displayTags();
				}
				else
				{
					m_TextArea.append("NO tags read\n"); 
				}
			}
			catch (Exception e)
			{
				m_TextArea.append(e.getMessage() + "\n");
			}
		}
		
		try
		{
			// Stop the continuous read mode.
			m_Reader.stopReadingTags();

			//*****
			//* Synchronize this block of code so that it cannot be
			//* executed at the same time as the updateUIControls method.
			//*****
			synchronized (this)
			{
				m_fContinuousReadInProgress = false;
				m_TextArea.append("I-Continuous read stopped.\n");
				m_BtnStopRead.setEnabled(false);
				m_BtnStartRead.setEnabled(true);
			}
		}
		catch (Exception e)
		{
			m_TextArea.append(e.getMessage() + "\n");
		}
	}
	
	/**
	 * Enables/disables the center trigger notifications. If enabled,
	 * it sets up to listen for the RFID button event and issue a RFID
	 * read when the center trigger button is pressed. This method
	 * is invoked from the item listener of the m_CBCenterTrigger
	 * check box.
	 * @param fEnabled true to enable center trigger notifications.
	 */
	public void setCenterTriggerNotify(boolean fEnabled)
	{
		try
		{
			if (fEnabled)
			{
				// First, let the reader know we would like to receive events
				// regarding the centertrigger button.
				m_Reader.setRFIDButtonEnabled(BRIReader.RFIDButtonIDs.CENTER, true);
				// Second, register an event listener to handle the RFID button event.
				m_Reader.addRFIDButtonListener(getCenterBtnAdapter());
			}
			else
			{
				m_Reader.setRFIDButtonEnabled(BRIReader.RFIDButtonIDs.CENTER, false);
				m_Reader.removeRFIDButtonListener(m_RFIDCenterBtnAdapter);
			}
		}
		catch (Exception e)
		{
			m_TextArea.append(e.getMessage() + "\n");
		}
	}
	
	/**
	 * Enables/disables the left scan button notifications. If enabled,
	 * it sets up to listen for the RFID button event and issue a RFID
	 * read when the left scan button is pressed. This method is invoked
	 * from the item listener of the m_CBLeftBtn check box.
	 * @param fEnabled true to enable left scan button notifications.
	 */
	public void setLeftButtonNotify(boolean fEnabled)
	{
		try
		{
			if (fEnabled)
			{
				// First, let the reader know we would like to receive events
				// regarding the left scan button.
				m_Reader.setRFIDButtonEnabled(BRIReader.RFIDButtonIDs.LEFT, true);
				// Second, register an event listener to handle the RFID button event.
				m_Reader.addRFIDButtonListener(getLeftBtnAdapter());
			}
			else
			{
				m_Reader.setRFIDButtonEnabled(BRIReader.RFIDButtonIDs.LEFT, false);
				m_Reader.removeRFIDButtonListener(m_RFIDLeftBtnAdapter);
			}
		}
		catch (Exception e)
		{
			m_TextArea.append(e.getMessage() + "\n");
		}
	}
	
	/**
	 * Enables/disables the middle scan button notifications. If enabled,
	 * it sets up to listen for the RFID button event and issue a RFID
	 * read when the middle scan button is pressed. This method is invoked
	 * from the item listener of the m_CBMiddleBtn check box.
	 * @param fEnabled true to enable middle scan button notifications.
	 */
	public void setMiddleButtonNotify(boolean fEnabled)
	{
		try
		{
			if (fEnabled)
			{
				// First, let the reader know we would like to receive events
				// regarding the middle scan button.
				m_Reader.setRFIDButtonEnabled(BRIReader.RFIDButtonIDs.MIDDLE, true);
				// Second, register an event listener to handle the RFID button event.
				m_Reader.addRFIDButtonListener(getMiddleBtnAdapter());
			}
			else
			{
				m_Reader.setRFIDButtonEnabled(BRIReader.RFIDButtonIDs.MIDDLE, false);
				m_Reader.removeRFIDButtonListener(m_RFIDMiddleBtnAdapter);
			}
		}
		catch (Exception e)
		{
			m_TextArea.append(e.getMessage() + "\n");
		}
	}

	/**
	 * Enables/disables the right scan button notifications. If enabled,
	 * it sets up to listen for the RFID button event and issue a RFID
	 * read when the right scan button is pressed. This method is invoked
	 * from the item listener of the m_CBRightBtn check box.
	 * @param fEnabled true to enable right scan button notifications.
	 */
	public void setRightButtonNotify(boolean fEnabled)
	{
		try
		{
			if (fEnabled)
			{
				// First, let the reader know we would like to receive events
				// regarding the right scan button.
				m_Reader.setRFIDButtonEnabled(BRIReader.RFIDButtonIDs.RIGHT, true);
				// Second, register an event listener to handle the RFID button event.
				m_Reader.addRFIDButtonListener(getRightBtnAdapter());
			}
			else
			{
				m_Reader.setRFIDButtonEnabled(BRIReader.RFIDButtonIDs.RIGHT, false);
				m_Reader.removeRFIDButtonListener(m_RFIDRightBtnAdapter);
			}
		}
		catch (Exception e)
		{
			m_TextArea.append(e.getMessage() + "\n");
		}
	}
	
	/**
	 * Gets a RFIDButtonAdapter object for handling the center trigger
	 * button events. The handler will issue a singleshot RFID read
	 * when the center trigger is pressed.
	 * @return a RFIDButtonAdapter object
	 */
	private RFIDButtonAdapter getCenterBtnAdapter()
	{
		if (null == m_RFIDCenterBtnAdapter)
		{
			m_RFIDCenterBtnAdapter = new RFIDButtonAdapter() {
				public void buttonPressed(RFIDButtonEvent aButtonEvent)
				{
					if (aButtonEvent.getButtonID() == BRIReader.RFIDButtonIDs.CENTER)
					{
						//*****
						//* Issue a singleshot read when the center trigger is pressed.
						//*****
						m_TextArea.append("I-Center trigger read.\n");
	
						try
						{
							// Read all tags in the field.
							if (true == m_Reader.read())
							{
								displayTags();
							}
							else
							{
								m_TextArea.append("NO tags read\n"); 
							}
						}
						catch (Exception e)
						{
							m_TextArea.append(e.getMessage() + "\n");
						}
					}
				} //end buttonPressed
			}; //end m_RFIDCenterBtnAdapter
		}
		return m_RFIDCenterBtnAdapter;
	}

	/**
	 * Gets a RFIDButtonAdapter object for handling the left scan
	 * button events. The handler will issue a singleshot RFID read
	 * when the left scan button is pressed.
	 * @return a RFIDButtonAdapter object
	 */
	private RFIDButtonAdapter getLeftBtnAdapter()
	{
		if (null == m_RFIDLeftBtnAdapter)
		{
			m_RFIDLeftBtnAdapter = new RFIDButtonAdapter() {
				public void buttonPressed(RFIDButtonEvent aButtonEvent)
				{
					if (aButtonEvent.getButtonID() == BRIReader.RFIDButtonIDs.LEFT)
					{
						//*****
						//* Issue a singleshot read when the left scan button is pressed.
						//*****
						m_TextArea.append("I-Left button read.\n");
	
						try
						{
							// Read all tags in the field.
							if (true == m_Reader.read())
							{
								displayTags();
							}
							else
							{
								m_TextArea.append("NO tags read\n"); 
							}
						}
						catch (Exception e)
						{
							m_TextArea.append(e.getMessage() + "\n");
						}
					}
				} //end buttonPressed
			}; //end m_RFIDLeftBtnAdapter
		}
		
		return m_RFIDLeftBtnAdapter;
	}
	
	/**
	 * Gets a RFIDButtonAdapter object for handling the middle scan
	 * button events. The handler will issue a singleshot RFID read
	 * when the middle scan button is pressed.
	 * @return a RFIDButtonAdapter object
	 */
	private RFIDButtonAdapter getMiddleBtnAdapter()
	{
		if (null == m_RFIDMiddleBtnAdapter)
		{
			m_RFIDMiddleBtnAdapter = new RFIDButtonAdapter() {
				public void buttonPressed(RFIDButtonEvent aButtonEvent)
				{
					if (aButtonEvent.getButtonID() == BRIReader.RFIDButtonIDs.MIDDLE)
					{
						//*****
						//* Issue a singleshot read when the middle scan button is pressed.
						//*****
						m_TextArea.append("I-Middle button read.\n");
	
						try
						{
							// Read all tags in the field.
							if (true == m_Reader.read())
							{
								displayTags();
							}
							else
							{
								m_TextArea.append("NO tags read\n"); 
							}
						}
						catch (Exception e)
						{
							m_TextArea.append(e.getMessage() + "\n");
						}
					}
				} //end buttonPressed
			}; //end m_RFIDMiddleBtnAdapter
		}
		
		return m_RFIDMiddleBtnAdapter;
	}

	/**
	 * Gets a RFIDButtonAdapter object for handling the right scan
	 * button events. The handler will issue a singleshot RFID read
	 * when the right scan button is pressed.
	 * @return a RFIDButtonAdapter object
	 */
	private RFIDButtonAdapter getRightBtnAdapter()
	{
		if (null == m_RFIDRightBtnAdapter)
		{
			m_RFIDRightBtnAdapter = new RFIDButtonAdapter() {
				public void buttonPressed(RFIDButtonEvent aButtonEvent)
				{
					if (aButtonEvent.getButtonID() == BRIReader.RFIDButtonIDs.RIGHT)
					{
						//*****
						//* Issue a singleshot read when the right scan button is pressed.
						//*****
						m_TextArea.append("I-Right button read.\n");
	
						try
						{
							// Read all tags in the field.
							if (true == m_Reader.read())
							{
								displayTags();
							}
							else
							{
								m_TextArea.append("NO tags read\n"); 
							}
						}
						catch (Exception e)
						{
							m_TextArea.append(e.getMessage() + "\n");
						}
					}
				} //end buttonPressed
			}; //end m_RFIDRightBtnAdapter
		}
		
		return m_RFIDRightBtnAdapter;
	}

	/**
	 * For each tag read, displays the tag key and the antenna number
	 * in the text box.
	 */
	public void displayTags()
	{
		int  iTagCount = m_Reader.getTagCount();

		// Displays the tags read
		for (int i=0; i<iTagCount; i++)
		{
			displayTag(m_Reader.tags[i]);
		}
	}
	
	public void displayTag(Tag aTag)
	{
		m_TextArea.append("Tag Key: " + aTag.toString());
		int iNumTagFields = aTag.tagFields.getItemCount();
		if (iNumTagFields > 0)
		{
			TagField tagFieldAnt = aTag.tagFields.getField(0);
			m_TextArea.append(" Ant: " + tagFieldAnt.getDataInt());
		}
		m_TextArea.append("\n");
	}
	
	
	/*
	 *	Closes the App. 
	 */
	public void exitApp()
	{
		if (m_Reader != null){
			m_Reader.dispose();
		}
		dispose();
		System.exit(0);
	}

}

