<?php
/**
 * ibm_maximo_api
 *
 * @package phppublisher
 * @author Alexander Kuballa
 * @copyright Copyright (c) 2008 - 2026, Alexander Kuballa
 * @license GNU GENERAL PUBLIC LICENSE Version 2 (see ../LICENSE.TXT)
 * @version 1.0
 */
require_once(CLASSDIR.'lib/interfaces/interfaces.api.class.php');

class ibm_maximo_api extends interfaces_api
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
		$this->response = $response;
		$this->user = $user;
		$this->db = $db;
		$this->file = $file;
		$this->settings = PROFILESDIR.'IBM.Maximo.ini';
		$this->ini = $this->file->get_ini($this->settings);
		$this->plugin = 'IBM.Maximo';
		$this->modules = array('facilities');
	}

}
?>
