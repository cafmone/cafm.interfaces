<?php
/**
 * IBM_Maximo_controller
 *
 * @package phppublisher
 * @author Alexander Kuballa
 * @copyright Copyright (c) 2008 - 2026, Alexander Kuballa
 * @license GNU GENERAL PUBLIC LICENSE Version 2 (see ../LICENSE.TXT)
 * @version 1.0
 */
require_once(CLASSDIR.'lib/interfaces/interfaces.controller.class.php');

class IBM_Maximo_controller extends interfaces_controller
{
	//--------------------------------------------
	/**
	 * Constructor
	 *
	 * @access public
	 * @param file $file
	 * @param htmlobject_response $response
	 * @param query $db
	 * @param user $user
	 */
	//--------------------------------------------
	function __construct( $file, $response, $db, $user ) {
		$this->classdir = CLASSDIR.'/lib/interfaces/';
		$this->file = $file;
		$this->profilesdir = PROFILESDIR;
		$this->settings = PROFILESDIR.'IBM.Maximo.ini';
		$this->ini = $this->file->get_ini($this->settings);
		$this->response = $response;
		$this->langdir = CLASSDIR.'/lib/interfaces/lang';
		$this->tpldir = CLASSDIR.'/lib/interfaces/templates';
		$this->user = $user;
		$this->db = $db;
		$this->plugin = 'IBM.Maximo';
		$this->modules = array('facilities');
		$this->lang['label'] = 'IBM Maximo';
		$this->lang['export']['legend_receiver'] = 'IBM Maximo';
	}

	//--------------------------------------------
	/**
	 * Cli
	 *
	 * @access public
	 * @return mixed
	 */
	//--------------------------------------------
	function cli() {
		require_once(CLASSDIR.'plugins/IBM.Maximo/class/IBM.Maximo.cli.class.php');
		$controller = new IBM_Maximo_cli($this);
		$controller->tpldir = $this->tpldir;
		$controller->actions_name = $this->actions_name;
		$data = $controller->action();
		return $data;
	}

}
?>
