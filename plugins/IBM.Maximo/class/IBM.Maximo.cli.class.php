<?php
/**
 * IBM_Maximo_cli
 *
 * @package phppublisher
 * @author Alexander Kuballa
 * @copyright Copyright (c) 2008 - 2026, Alexander Kuballa
 * @license GNU GENERAL PUBLIC LICENSE Version 2 (see ../LICENSE.TXT)
 * @version 1.0
 */
require_once(CLASSDIR.'lib/interfaces/interfaces.cli.class.php');
class IBM_Maximo_cli extends interfaces_cli {
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
	function __construct($controller) {
		$this->controller = $controller;
		$this->response = $controller->response;
		$this->user = $controller->user;
		$this->db = $controller->db;
		$this->file = $controller->file;
	}

	//--------------------------------------------
	/**
	 * facilities import
	 *
	 * @access public
	 */
	//--------------------------------------------
	function facilities( $visible = false, $return = false ) {
		if($visible === true) {
			require_once(CLASSDIR.'plugins/IBM.Maximo/class/IBM.Maximo.api.class.php');
			$a = new IBM_Maximo_api($this->file, $this->response, $this->db, $this->user);
			$a->facilities(true);
		}
	}
}
?>
